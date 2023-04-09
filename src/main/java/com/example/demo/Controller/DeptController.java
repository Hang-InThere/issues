package com.example.demo.Controller;


import com.example.demo.Entity.Assignment;
import com.example.demo.Entity.Dept;
import com.example.demo.Entity.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class DeptController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @RequestMapping("/depts")
    public String list(Model model){
        String sql = "select* from assignment where eid is null";
        Collection<Assignment> assignments = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Assignment>(Assignment.class));
        model.addAttribute("assignments",assignments);
        return "list2";
    }

    @GetMapping("/dept")
    public String toAddpage(){

        return"add1";
    }
    @PostMapping("/dept/{id}")
    public String addDep(Assignment assignment,@PathVariable("id") String id){
        String sql="insert into assignment values(?,?,?,?,?,?,?,?)";
        Object[] args = {assignment.getId(),assignment.getName(),assignment.getStartTime(),assignment.getDeadLine(),
            assignment.getMoney(),assignment.getResume(),null,null};
        jdbcTemplate.update(sql,args);
        return"redirect:/depts";
    }
    @GetMapping("/dep/{id}")
    public String toUpdatepage(@PathVariable("id")String id,Model model){
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
    public String delete(@PathVariable("id")String id){
        String sql = "delete from assignment where id="+id;
        jdbcTemplate.update(sql);
        return"redirect:/depts";
    }
    @PostMapping("/search1")
    public String Search(@RequestParam("msg") String msg,Model model){
        String sql = "select* from assignment where eid is null and (id like '%"+msg+"%'or name like '%"
                +msg+"%'or startTime like'%"+msg+"%'or deadLine like'%"+msg+"%'or money like'%"+msg+"%'or resume like '%"+msg+"%')";
        Collection<Assignment> assignments = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Assignment>(Assignment.class));
        model.addAttribute("assignments",assignments);
        return "list2";
    }
}
