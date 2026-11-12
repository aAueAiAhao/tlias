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

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

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

    @Transactional(rollbackFor = Exception.class)//事务控制
    @Override
    public void insert(Emp emp) {
        try{
            //1.补全员工信息
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            //2.添加员工信息到数据库
            empMapper.insert(emp);

            int i = 1/0;

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
}
