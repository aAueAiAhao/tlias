package com.itheima.service;

import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.JobOption;
import com.itheima.pojo.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface EmpService {

    PageResult page(EmpQueryParam empQueryParam);

    void insert(Emp emp);

    void deleteByIds(List<Integer> ids);

    Emp getInfo(Integer id);

    void update(Emp emp);

    JobOption reportCountByDept();

    List<Map> reportCountByGender();
}
