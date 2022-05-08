package com.example.demo.Controller;


import com.example.demo.Entity.Assignment;
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
        String sql = "select* from assignment";
        Collection<Assignment> assignments = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Assignment>(Assignment.class));
        for(Assignment a :assignments){
            System.out.println(a.getId());
            System.out.println(a.getName());
            System.out.println(a.getMoney());
        }

        model.addAttribute("assignments",assignments);
        return "list2";
    }

    @GetMapping("/dept")
    public String toAddpage(){

        return"add1";
    }
    @PostMapping("/dept")
    public String addDep(Assignment assignment){
        String sql="insert into dept values(?,?,?,?,?,?)";
        Object[] args = {assignment.getId(),assignment.getName(),assignment.getStartTime(),assignment.getDeadLine(),
            assignment.getMoney(),assignment.getResume()};
        jdbcTemplate.update(sql,args);
        return"redirect:/depts";
    }
    @GetMapping("/dep/{id}")
    public String toUpdatepage(@PathVariable("id")Integer id,Model model){
        String sql = "select* from assignment where id="+id;
        Assignment assignment =  jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Assignment>(Assignment.class));
        model.addAttribute("assignment",assignment);
        return "update1";

    }
    @PostMapping("/updateDept")
    public String updateDept(Assignment assignment){
        String sql = "update assignment set name=?,startTime=?,deadLine=?,money=?,resume=? where id=?";
        Object[] args = {assignment.getName(),assignment.getStartTime(),assignment.getDeadLine(),assignment.getMoney(),assignment.getResume(),assignment.getId()};
        jdbcTemplate.update(sql,args);
        return"redirect:/depts";

    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")Integer id){
        String sql = "delete from assignment where id="+id;


        jdbcTemplate.update(sql);

        return"redirect:/depts";
    }
}
