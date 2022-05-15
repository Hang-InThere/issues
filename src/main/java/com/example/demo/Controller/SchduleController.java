package com.example.demo.Controller;

import com.example.demo.Entity.Assignment;
import com.example.demo.Entity.Schedule;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class SchduleController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @RequestMapping("/sche")
    public String list(Model model){
        String sql = "select* from schedule where (state = '待开始'or state = '执行中') and euserid = '"+LoginController.id+"'";
        Collection<Schedule> schedules = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Schedule>(Schedule.class));

        model.addAttribute("schedules",schedules);
        return "schedule";
    }
    @PostMapping("/search3")
    public String Search(@RequestParam("msg")String msg, Model model){
        String sql = "select* from schedule where id like '%"+msg+"%'or name like '%"
                +msg+"%'or startTime like'%"+msg+"%'or deadLine like'%"+msg+"%' and euserid = '"+LoginController.id+"'";
        Collection<Schedule> schedules = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Schedule>(Schedule.class));

        model.addAttribute("schedules",schedules);
        return "schedule";
    }
    @PostMapping("/search4")
    public String Search1(@RequestParam("msg")String msg, Model model){
        String sql = "select* from schedule where id like '%"+msg+"%'or name like '%"
                +msg+"%'or startTime like'%"+msg+"%'or deadLine like'%"+msg+"%' and euserid = '"+LoginController.id+"'";
        Collection<Schedule> schedules = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Schedule>(Schedule.class));

        model.addAttribute("schedules",schedules);
        return "history";
    }
    @PostMapping("/search5")
    public String Search5(@RequestParam("msg")String msg, Model model){
        String sql = "select schedule.id,schedule.name,startTime,deadLine,state,emp.name ename from schedule,emp where  emp.id = euserid and (schedule.id like '%"+msg+"%'or schedule.name like '%"
                +msg+"%'or startTime like'%"+msg+"%'or deadLine like'%"+msg+"%' or state like '%"+msg+"%')";
        Collection<Schedule> schedules = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Schedule>(Schedule.class));

        model.addAttribute("schedules",schedules);
        return "doing";
    }
    @GetMapping("/start/{id}")
    public String start(@PathVariable("id")String id,Model model){
        String sql = "update schedule set state = '执行中' where id = "+id;
        jdbcTemplate.update(sql);
        return "redirect:/sche";
    }

    @RequestMapping("/his")
    public String list1(Model model){
        String sql = "select* from schedule where (state = '已完成' or state = '已结算') and euserid = '"+LoginController.id+"'";
        Collection<Schedule> schedules = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Schedule>(Schedule.class));

        model.addAttribute("schedules",schedules);
        return "history";
    }
    @GetMapping("/finish/{id}")
    public String finfish(@PathVariable("id")String id){
        String sql = "update schedule set state = '已完成' where id = "+id;
        jdbcTemplate.update(sql);
        return "redirect:/his";
    }
    @RequestMapping("/do")
    public String list3(Model model){
        String sql = "select schedule.id,schedule.name,startTime,deadLine,state,emp.name ename from schedule,emp where emp.id = euserid";
        Collection<Schedule> schedules = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Schedule>(Schedule.class));
        model.addAttribute("schedules",schedules);
        return "doing";
    }
    @GetMapping("/js/{id}")
    public String pay(@PathVariable("id")String id){
        String sql = "update schedule set state = '已结算' where id = "+id;
        jdbcTemplate.update(sql);
        return "redirect:/do";
    }

}
