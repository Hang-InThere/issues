package com.example.demo.Controller;

import com.example.demo.Entity.Assignment;
import com.example.demo.Entity.Schedule;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
public class SchduleController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @RequestMapping("/sche")
    public String list(Model model){
        String sql = "select* from schedule";
        Collection<Schedule> schedules = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Schedule>(Schedule.class));

        model.addAttribute("schedules",schedules);
        return "schedule";
    }
    @PostMapping("/search3")
    public String Search(@RequestParam("msg")String msg, Model model){
        String sql = "select* from schedule where id like '%"+msg+"%'or name like '%"
                +msg+"%'or startTime like'%"+msg+"%'or deadLine like'%"+msg+"%'";
        Collection<Schedule> schedules = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Schedule>(Schedule.class));

        model.addAttribute("schedules",schedules);
        return "schedule";
    }

}
