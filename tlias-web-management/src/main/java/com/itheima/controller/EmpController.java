package com.itheima.controller;

import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/*
*员工管理的controller
* */

@Slf4j
@RequestMapping("/emps")
@RestController()
public class EmpController {
    @Autowired
    private EmpService empService;



    @GetMapping
    public Result page(EmpQueryParam empQueryParam) {
        PageResult<Emp> pageResult=empService.page(empQueryParam);
        log.info("查询请求参数:{}",empQueryParam);
        return Result.success(pageResult);
    }


    @PostMapping
    public Result save(@RequestBody Emp emp) {
        log.info("添加员工参数:{}",emp);
        empService.insert(emp);
        return Result.success();
    }

    /*批量删除员工*/
    @DeleteMapping
    public Result deleteEmpByIds(@RequestParam List<Integer> ids) {
        log.info("批量删除员工ids:{}",ids);
        empService.deleteByIds(ids);
        return Result.success();
    }

    /*
    * 查询回显,修改员工信息
    * */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        log.info("根据id回显用户属性:{}",id);
        Emp getInfoEmp=empService.getInfo(id);
        return Result.success(getInfoEmp);
    }
    /*
    * 修改员工
    * */
    @PutMapping
    public Result update(@RequestBody Emp emp) {
        log.info("修改员工,emp:{}",emp);
        empService.update(emp);
        return Result.success();
    }
}
