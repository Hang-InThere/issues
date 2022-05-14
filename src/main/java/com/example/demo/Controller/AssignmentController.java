package com.example.demo.Controller;

import com.example.demo.Entity.Assignment;
import com.example.demo.Entity.Dept;
import com.example.demo.Entity.Emp;
import com.example.demo.Entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class AssignmentController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/assigns")
    public String list(Model model){

        String sql = "select* from assignment";
        Collection<Assignment> assignments = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Assignment>(Assignment.class));
        model.addAttribute("assignments",assignments);
        return "list3";
    }
    @GetMapping("/assign/{id}")
    public String toSearchpage(@PathVariable("id")Integer id, Model model){

        String sql = "select* from assignment where id="+id;
        Assignment assignments =  jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Assignment>(Assignment.class));
        model.addAttribute("assignments",assignments);
        return "search";
    }
    @PostMapping("/apply")
    public String apply(Assignment assignment,Model model){
        try {
            String sql = "insert into schedule values(?,?,?,?)";
            Object[] args = {assignment.getId(),assignment.getName(),assignment.getStartTime(),assignment.getDeadLine()};
            jdbcTemplate.update(sql,args);
            return"redirect:/sche";

        }catch (Exception e){
            model.addAttribute("assignments",assignment);
            model.addAttribute("msg","已申请");
            return "search";
        }

    }
    @PostMapping("/search2")
    public String Search(@RequestParam("msg")String msg,Model model){
        String sql = "select* from assignment where id like '%"+msg+"%'or name like '%"
                +msg+"%'or startTime like'%"+msg+"%'or deadLine like'%"+msg+"%'or money like'%"+msg+"%'or resume like '%"+msg+"%'";
        Collection<Assignment> assignments = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Assignment>(Assignment.class));
        model.addAttribute("assignments",assignments);
        return "list3";
    }

}
