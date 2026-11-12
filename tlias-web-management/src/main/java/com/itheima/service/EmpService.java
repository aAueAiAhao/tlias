package com.itheima.service;

import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.PageResult;
import org.springframework.stereotype.Service;


public interface EmpService {

    PageResult page(EmpQueryParam empQueryParam);

    void insert(Emp emp);
}
