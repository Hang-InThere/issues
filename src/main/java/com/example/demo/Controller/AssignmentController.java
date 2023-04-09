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
    /*
        展示所有任务
     */
   @RequestMapping("/assigns")
    public String list( Model model){

        String sql = "select* from assignment where eid is null";
        Collection<Assignment> assignments = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Assignment>(Assignment.class));
      ;
        model.addAttribute("assignments",assignments);

        return "list3";
    }
    /*
        跳转到查看页面
     */
    @GetMapping("/assign/{id}")
    public String toSearchpage(@PathVariable("id")String id, Model model){
        String sql = "select* from assignment where id="+id;
        Assignment assignments =  jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Assignment>(Assignment.class));
        model.addAttribute("assignments",assignments);
        return "search";
    }
    /*
        申请任务
     */
    @PostMapping("/apply")
    public String apply(Assignment assignment,Model model){
        try {
            String sql = "insert into schedule values(?,?,?,?,?,?)";
            String sql2 = "update assignment set eid = '"+LoginController.id+"' where id ='"+assignment.getId()+"'";
            String state = "待开始";
            Object[] args = {assignment.getId(),assignment.getName(),assignment.getStartTime(),assignment.getDeadLine(),LoginController.id,state};
            jdbcTemplate.update(sql,args);
            jdbcTemplate.update(sql2);
            return"redirect:/sche";

        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("assignments",assignment);
            model.addAttribute("msg","已申请");
            return "search";
        }

    }
    /*
        查询
     */
    @PostMapping("/search2")
    public String Search(@RequestParam("msg")String msg,Model model){
        String sql = "select* from assignment where  eid is null and  (id like '%"+msg+"%'or name like '%"
                +msg+"%'or startTime like'%"+msg+"%'or deadLine like'%"+msg+"%'or money like'%"+msg+"%'or resume like '%"+msg+"%') ";
        Collection<Assignment> assignments = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Assignment>(Assignment.class));
        model.addAttribute("assignments",assignments);
        return "list3";
    }

}
