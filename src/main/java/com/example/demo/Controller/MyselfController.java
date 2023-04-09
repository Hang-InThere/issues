package com.example.demo.Controller;

import com.example.demo.Entity.Emp;
import com.example.demo.Entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
public class MyselfController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @RequestMapping("/mys")
    public String toMyself(Model model,Model model2){
        String sql = "select* from emp where id = "+LoginController.id;
        String sql1 = "select assignment.name,assignment.startTime,assignment.deadLine,money,state from schedule,assignment where assignment.id = schedule.id and euserid" +
                "="+LoginController.id+" and state = '已结算'";
        Emp emps =  jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Emp>(Emp.class));

        try {
            Collection<History> history = jdbcTemplate.query(sql1,new BeanPropertyRowMapper<History>(History.class));
            model.addAttribute("his",history);
        }catch (Exception e){

        }
        model2.addAttribute("emps",emps);
        return "mine";
    }
    @RequestMapping("/modify")
    public String Modify(Emp emp){
        String sql = "update emp set name=?,sex=?,profession=?,phone=?,resume=?,birthday=? where id="+LoginController.id;
        Object[] args = {emp.getName(),emp.getSex(),emp.getProfession(),emp.getPhone(),emp.getResume(),emp.getBirthday()};
        jdbcTemplate.update(sql,args);
        return "redirect:/mys";
    }

}
