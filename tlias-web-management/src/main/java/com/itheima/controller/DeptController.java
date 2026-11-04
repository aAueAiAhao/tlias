package com.itheima.controller;

import org.springframework.web.bind.annotation.RestController;
//相当于加了两个注解@controller和@ResponseBody(将方法的返回值直接作为数据响应返回给前端)
//如果返回值是对象或者集合会转为json在传递给前端
@RestController
public class DeptController {
}
