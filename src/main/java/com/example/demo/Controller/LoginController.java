package com.example.demo.Controller;

import com.example.demo.Entity.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @RequestMapping("/user/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("identity")int identity, Model model ,HttpSession session){

        String sql = "select * from employee.user where username="+"'"+username+"'";
        Account account = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Account>(Account.class));
        if(!StringUtils.isEmpty(username) && account.getPassword().equals(password)){
            if(account.getIdentity().equals(identity)){
                if(identity == 0){
                    session.setAttribute("loginuser",username);
                    return "redirect:/main.html";
                }else{
                    session.setAttribute("loginuser",username);
                    return "redirect:/freelancer.html";
                }

            }else {
                model.addAttribute("msg","身份异常");
                return "index";
            }


        }else{
            model.addAttribute("msg","用户名或密码错误");
            return "index";
        }


    }
    @RequestMapping("user/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/index.html";
    }
    @RequestMapping("/register")
    public String toRegister(){
        return"register";
    }

    @RequestMapping("/user/register")
    public String Register(@RequestParam("username") String username, @RequestParam("password") String password,@RequestParam("repassword")
                           String repassword,@RequestParam("identity")int identity,Model model){
        try{
            if(password.equals(repassword)){
                String sql = "insert into employee.user values(?,?,?)";
                Object[] args = {username,password,identity};
                jdbcTemplate.update(sql,args);
                model.addAttribute("msg","注册成功");
                return "redirect:/index.html";
            }else {

                model.addAttribute("msg","密码不一致");
                return "register";
            }


        }catch (Exception e){

            model.addAttribute("msg1","用户名已存在");
            return "register";
        }



    }
}
