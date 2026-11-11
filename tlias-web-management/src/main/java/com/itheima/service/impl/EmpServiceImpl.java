package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Emp;
import com.itheima.pojo.PageResult;
import com.itheima.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;

    @Override
    public PageResult<Emp> page(Integer page, Integer pageSize) {
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
        PageHelper.startPage(page, pageSize);
        //2.执行查询
        List<Emp> list = empMapper.list();
        Page<Emp> p = (Page<Emp>) list;
        //3.封装结果
        return new PageResult<Emp>(p.getTotal(), p.getResult());
    }
}
