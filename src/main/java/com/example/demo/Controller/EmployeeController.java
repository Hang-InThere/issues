package com.example.demo.Controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.example.demo.Entity.Emp;

import com.example.demo.Entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class EmployeeController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/emps")
    public String list(Model model){



        String sql = "select* from emp";
        Collection<Emp> emps = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Emp>(Emp.class));
        model.addAttribute("emps",emps);
        return "list";
    }
    @GetMapping("/emp")
    public String toAddpage(){

        return"add";
    }

    @PostMapping ("/emp")
    public String addEmp(Emp employee){
        String sql = "insert into emp values(?,?,?,?,?,?,?,?)";
        Object[] args = {employee.getId(),employee.getName(),employee.getSex(),employee.getPhone(),
                           employee.getProfession(),employee.getResume(),employee.getBirthday(),employee.getBalance()};
        jdbcTemplate.update(sql,args);

        return"redirect:/emps";
    }
    @GetMapping("/emp/{id}")
    public String toUpdateEmp(@PathVariable("id")String id, Model model){

        String sql = "select* from emp where id="+id;
        Emp emps =  jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Emp>(Emp.class));
        model.addAttribute("emps",emps);
        return "update";
    }
    @PostMapping("/updateEmp")
    public String UpdateEmp(Emp employee){
        String sql = "update emp set name=?,sex=?,phone=?,profession=?,resume=? where id=?";
        Object[] args = {employee.getName(),employee.getSex(),employee.getPhone(),
                employee.getProfession(),employee.getResume(),employee.getId()};
        jdbcTemplate.update(sql,args);
        return "redirect:/emps";
    }
    @GetMapping("/delemp/{id}")
    public String DeleteEmp(@PathVariable("id")String id){
        String sql = "delete from emp where id="+id;
        jdbcTemplate.update(sql);
        return "redirect:/emps";

    }
    @PostMapping("/search")
    public String Search(@RequestParam("msg") String msg  , Model model){
        if(msg.equals("女")){
            String sql = "select* from emp where sex=0";
            Collection<Emp> emps = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Emp>(Emp.class));
            model.addAttribute("emps",emps);
        }else if(msg.equals("男")){
            String sql = "select* from emp where sex=1";
            Collection<Emp> emps = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Emp>(Emp.class));
            model.addAttribute("emps",emps);
        }else{
            String sql = "select* from emp where id like '%"+msg+"%'or name like '%"+msg+"%'or sex like'%"+msg+"%'or phone like'%"+msg+"%'or profession like'%"+msg+"%'or resume like '%"+msg+"%'";
            Collection<Emp> emps = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Emp>(Emp.class));

            model.addAttribute("emps",emps);
        }

        return "list";
    }
    @GetMapping("/personal/{id}")
    public String toPersoner(@PathVariable("id")String id,Model model,Model model2){

        String sql = "select* from emp where id = "+id;
        String sql1 = "select assignment.name,assignment.startTime,assignment.deadLine,money,state from schedule,assignment where assignment.id = schedule.id and euserid" +
                "="+id+" and state = '已结算'";
        Emp emps =  jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Emp>(Emp.class));
        try {
            Collection<History> history = jdbcTemplate.query(sql1,new BeanPropertyRowMapper<History>(History.class));
            model.addAttribute("his",history);
        }catch (Exception e){

        }

        model2.addAttribute("emps",emps);

        return "personal";
    }
}
