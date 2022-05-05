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
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
public class AssignmentController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/assigns")
    public String list(Model model){

        String sql = "select* from assignment";
        Collection<Assignment> assignments = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Assignment>(Assignment.class));
        for(Assignment a :assignments){
            System.out.println(a.getId());
        }
        model.addAttribute("assignments",assignments);
        return "list3";
    }
    @GetMapping("/assign/{id}")
    public String toSearchpage(@PathVariable("id")Integer id, Model model){

        String sql = "select* from assignment where id="+id;
        Assignment assignments =  jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Assignment>(Assignment.class));
        System.out.println("****************************************************************");
        model.addAttribute("assignments",assignments);
        return "search";
    }

}
