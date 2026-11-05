package com.itheima.service;

import com.itheima.pojo.Dept;

import java.util.List;

public interface DeptService {
    //接口创建方法是为了DeptController里面的多态使用
    //查询所有数据
    List<Dept> findAll();


    //删除部门
    void deleteById(Integer deptId);

    //添加部门
    void add(Dept dept);
}
