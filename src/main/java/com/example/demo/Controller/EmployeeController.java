package com.example.demo.Controller;

import com.example.demo.Entity.Emp;

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
        System.out.println("===================================");
        String sql = "insert into emp values(?,?,?,?,?,?)";
        Object[] args = {employee.getId(),employee.getName(),employee.getSex(),employee.getPhone(),
                           employee.getProfession(),employee.getResume()};
        jdbcTemplate.update(sql,args);

        return"redirect:/emps";
    }
    @GetMapping("/emp/{id}")
    public String toUpdateEmp(@PathVariable("id")Integer id, Model model){

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
    public String DeleteEmp(@PathVariable("id")Integer id){
        String sql = "delete from emp where id="+id;
        jdbcTemplate.update(sql);
        return "redirect:/emps";

    }
    @PostMapping("/search")
    public String Search(@RequestParam("msg") String msg  , Model model){
        String sql = "select* from emp where id like '%"+msg+"%'or name like '%"+msg+"%'or sex like'%"+msg+"%'or phone like'%"+msg+"%'or profession like'%"+msg+"%'or resume like '%"+msg+"%'";
        Collection<Emp> emps = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Emp>(Emp.class));

        model.addAttribute("emps",emps);
        return "list";
    }
}
