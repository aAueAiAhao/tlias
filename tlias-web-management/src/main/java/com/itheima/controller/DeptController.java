package com.itheima.controller;
import com.itheima.pojo.Dept;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;
import com.sun.source.doctree.DeprecatedTree;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//底层创建类logger对象
@Slf4j
@RequestMapping("/depts")
//相当于加了两个注解@controller和@ResponseBody(将方法的返回值直接作为数据响应返回给前端)
//如果返回值是对象或者集合会转为json在传递给前端
@RestController
public class DeptController {
    //导入日志
    //private static final Logger log= LoggerFactory.getLogger(DeptController.class);


    @Autowired
    //注意要私有因为有封装特性不让别的类修改它,可以保护程序
    //私有了也可以注入不受private影响,底层原理是反射
    private DeptService deptService;


    //@RequestMapping(value = "/depts",method = RequestMethod.GET)
    @GetMapping/*("/depts")*///post,delete,put同类
    public Result list(){
        //System.out.println("查询所有部门的数据");
        log.info("查询所有部门的数据");
        //Result result = new Result();
        //静态方法可以直接用类名调用,不需要创建对象了
       return Result.success(deptService.findAll());
        }


    @DeleteMapping/*("/depts")*/
    //若接收属性与形参相同可省略@RequestParam
    public Result delete(@RequestParam("id") Integer deptId){
        //值传递给deptService实现类的deptId属性
        log.info("根据id删除部门:{}",deptId);
        deptService.deleteById(deptId);
        return Result.success();
    }


    //添加部门
    @PostMapping/*("/depts")*/
    public Result add(@RequestBody Dept dept){
        log.info("添加部门:{}",dept);
        deptService.add(dept);
        return Result.success();
    }

    //查询回填
    @GetMapping("/{id}")
    public Result findById(@PathVariable("id") Integer id){
        log.info("根据id查询数据:{}",id);
        Dept dept=deptService.findById(id);
        return Result.success(dept);
    }
    //修改数据
    @PutMapping/*("/depts")*/
    public Result update(@RequestBody Dept dept){
        log.info("修改数据:{}",dept);
        deptService.update(dept);
        return Result.success();
    }
    }

