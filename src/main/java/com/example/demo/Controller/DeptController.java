package com.example.demo.Controller;


import com.example.demo.Entity.Dept;
import com.example.demo.Entity.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
public class DeptController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @RequestMapping("/depts")
    public String list(Model model){
        String sql = "select* from dept";
        Collection<Dept> deps = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Dept>(Dept.class));

        model.addAttribute("deps",deps);
        return "list2";
    }

    @GetMapping("/dept")
    public String toAddpage(){

        return"add1";
    }
    @PostMapping("/dept")
    public String addDep(Dept dept){
        String sql="insert into dept values(?,?,?)";
        Object[] args = {dept.getId(),dept.getDeptName(),dept.getManager()};
        jdbcTemplate.update(sql,args);
        return"redirect:/depts";
    }
    @GetMapping("/dep/{id}")
    public String toUpdatepage(@PathVariable("id")Integer id,Model model){
        String sql = "select* from dept where id="+id;
        Dept dept =  jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Dept>(Dept.class));
        model.addAttribute("depts",dept);
        return "update1";

    }
    @PostMapping("/updateDept")
    public String updateDept(Dept dept){
        String sql = "update dept set deptName=?,manager=? where id=?";
        Object[] args = {dept.getDeptName(),dept.getManager(),dept.getId()};
        jdbcTemplate.update(sql,args);
        return"redirect:/depts";

    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")Integer id){
        String sql = "delete from dept where id="+id;
        String sql1 = "select * from emp where deptId="+"'"+id+"'";

        jdbcTemplate.update(sql);
        Emp emp = jdbcTemplate.queryForObject(sql1, new BeanPropertyRowMapper<Emp>(Emp.class));
        String sql2 = "update emp set deptId=null,deptName=null where id="+"'"+emp.getId()+"'";
        jdbcTemplate.update(sql2);
        return"redirect:/depts";
    }
}
