package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.EmpExprMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.*;
import com.itheima.service.EmpLogService;
import com.itheima.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;
    @Autowired
    private EmpLogService empLogService;

    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        //原理版
        /*
        //1.查询数据量
        long total= empMapper.count();
        //2.返回数据值
        Integer start = (page-1)*pageSize;
        List<Emp> empList=empMapper.list(start,pageSize);
        */
        //pageHelper版
        //1.设置分页参数
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());
        //2.执行查询
        List<Emp> list = empMapper.list(empQueryParam);
        Page<Emp> p = (Page<Emp>) list;
        //3.封装结果
        return new PageResult<Emp>(p.getTotal(), p.getResult());


    }
    //事务控制rollbackFor = Exception.class是指定回滚的异常类型
    @Transactional(rollbackFor = Exception.class)

    @Override
    public void insert(Emp emp) {
        try{
            //1.补全员工信息
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            //2.添加员工信息到数据库
            empMapper.insert(emp);

            //3.添加员工经历
            Integer empId = emp.getId();
            List<EmpExpr> empExprList = emp.getExprList();
            if (!CollectionUtils.isEmpty(empExprList)) {
                empExprList.forEach(empExpr ->
                        //设置每段经历id
                        empExpr.setEmpId(empId));
                //批量插入数据库
                empExprMapper.insertBatch(empExprList);
            }
        } finally {
            EmpLog empLog = new EmpLog(null,LocalDateTime.now(),emp.toString());
            empLogService.insertLog(empLog);
        }

    }


    /*
    *批量删除员工
    * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Integer> ids) {
        //1.删除员工表员工
        empMapper.deletEmpByIds(ids);

        //2.删除工作经历表员工
        empExprMapper.deleteByEmpIds(ids);
    }


    /*
    *查询回显
    *修改员工部门
    * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Emp getInfo(Integer id) {
       return empMapper.getInfo(id);
    }

    /*
    * 修改员工信息
    * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Emp emp) {
        //1.修改员工基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

        //2.修改员工经历
        //2.1删除原来的工作经历
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));
        //2.2添加新的工作经历
        Integer empId = emp.getId();
        List<EmpExpr> empExprList = emp.getExprList();
        if (!CollectionUtils.isEmpty(empExprList)) {
            empExprList.forEach(empExpr -> empExpr.setEmpId(empId));
            empExprMapper.insertBatch(empExprList);
        }



    }


    /*
    * 根据部门统计人数
    * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JobOption reportCountByDept() {
        //先接取数据
        List<Map<String,Object>> list=empMapper.getCountByDeptId();
        //1.把部门封装到jobList
        List<Object> jobList= list.stream().map(dataMap->dataMap.get("pos")).toList();
        //2.把人数封装到dataList
        List<Object> dataList= list.stream().map(dataMap->dataMap.get("total")).toList();
        return new JobOption(jobList,dataList);
    }


    /*
    * 根据性别统计人数
    * */
    @Override
    public List<Map> reportCountByGender() {
        return empMapper.getCountByGender();
    }
}
