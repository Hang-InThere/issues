//package com.example.demo.Controller;


import com.example.demo.Entity.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
/*
@RestController
public class SqlController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/userlist")
    public List<Map<String,Object>> userList(){
        String sql = "select* from user";
        List<Map<String,Object>> list_map = jdbcTemplate.queryForList(sql);

        return list_map;
    }
    @GetMapping("/adduser")
    public String addUser(){
        String sql = "insert into employee.user(username,password) values ('张三','123456')";
        jdbcTemplate.update(sql);
        return "add-ok";
    }
    @GetMapping("/updateuser/{username}")
    public String updateUser(@PathVariable("username") String username){
        String sql = "update employee.user set username=?,password=? where username="+username;
        Object[] objects = new Object[2];
        objects[0] = "小明";
        objects[1] = "0000";
        jdbcTemplate.update(sql,objects);
        return "update-ok";
    }
    @GetMapping("/deleteuser/{username}")
    public String deleteUser(@PathVariable("username") String username){
        String sql = "delete from employee.user  where username="+username;
        jdbcTemplate.update(sql);
        return "delete-ok";
    }
    @GetMapping("/list")
    public List<Emp> Test(){
        String sql = "select* from emp";
        List<Emp> emps = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Emp>(Emp.class));
        return emps;
    }

}*/
