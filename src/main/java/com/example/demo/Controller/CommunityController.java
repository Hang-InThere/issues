package com.example.demo.Controller;

import com.example.demo.Entity.Community;
import com.example.demo.Entity.Emp;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class CommunityController {
    String picture;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/com")
    public String toCommunity(Model model){
        String sql = "select* from community where eid = "+LoginController.id+" order by time desc";
        List<Community> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Community>(Community.class));
        model.addAttribute("com",list);
        return "community";
    }
    @RequestMapping("/all")
    public String showAllCommunity(Model model){
        String sql = "select * from community order by time desc";
        List<Community> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Community>(Community.class));
        model.addAttribute("com",list);
        return "allcommunity";
    }

    @RequestMapping("/toaddCommunity")
    public String toAddCommunity(){
        return "addcommunity";
    }
    @RequestMapping("/uploadCom")
    public String upLoadpicture(Model model, MultipartFile file, HttpServletRequest request){
        /*（1）判断是否选择了文件和文件大小是否为0*/
        if(file.getSize()!=0&&file!=null)
        {
            //（2）先指定上传路径，获取对应物理路径
            File targetDirectory = new File("e:/Employee/images");

            /*（3）获取上传的文件名*/
            String temFileName = file.getOriginalFilename();
            System.out.println(temFileName);

            /*（4）判断文件格式*/
            int dot = temFileName.lastIndexOf('.');
            String ext = "";  //文件后缀名
            if ((dot > -1) && (dot < (temFileName.length() - 1))) {
                ext = temFileName.substring(dot + 1);
            }
            // 其他文件格式不处理
            if ("png".equalsIgnoreCase(ext) || "jpg".equalsIgnoreCase(ext) || "gif".equalsIgnoreCase(ext)|| "jpeg".equalsIgnoreCase(ext))
            {
                // (5)重命名上传的文件名
                String targetFileName = renameFileName(temFileName);

                // 保存的新文件
                File target = new File(targetDirectory, targetFileName);
                System.out.println(target.getAbsolutePath());

                try {
                    //（6）保存文件,上传
                    FileUtils.copyInputStreamToFile(file.getInputStream(), target);
                    picture = targetFileName;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                model.addAttribute("icon",targetFileName);
                return "addcommunity";
            }
            model.addAttribute("tips","选择的文件不是图片，请重新选择！");
            return "addcommunity";
        }
        model.addAttribute("tips","未选择文件");
        return "addcommunity";
    }
    @RequestMapping("/fabu")
    public String fabu(@RequestParam("xlh")String xlh){
        String sql = "select* from emp where id = "+LoginController.id;
        Emp emp = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Emp>(Emp.class));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Object[] obj = {0,emp.getName(),emp.getIconName(),xlh,picture,emp.getId(),df.format(new Date())};
        String sql1 = "insert into community values(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql1,obj);
        return"redirect:/com";
    }

    /*文件重命名方法*/
    public static String renameFileName(String fileName) {
        String formatDate = new SimpleDateFormat("yyMMddHHmmss").format(new Date()); // 当前时间字符串
        int random = new Random().nextInt(10000);
        String extension = fileName.substring(fileName.lastIndexOf(".")); // 文件后缀

        return formatDate + random + extension;
    }
}
