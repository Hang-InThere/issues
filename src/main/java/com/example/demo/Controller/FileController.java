package com.example.demo.Controller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class FileController {
        @Autowired
        JdbcTemplate jdbcTemplate;


        @RequestMapping("/toUpload/{id}")
        public String toUpload(Model model,@PathVariable("id")String id){
            model.addAttribute("id",id);
            return "upload";
        }

        @RequestMapping("/upload/{id}")
        public String upload(Model model, MultipartFile file, HttpServletRequest request,@PathVariable("id")String id) throws IOException {

            String originalFilename = file.getOriginalFilename();
            String renameFileName = renameFileName(originalFilename);
            File targetDirectory = new File("e:/Employee/upload");
            file.transferTo(new File(targetDirectory,renameFileName));
            String sql = "insert into file values(?,?,?)";
            Object[] args = {0,originalFilename,renameFileName};
            jdbcTemplate.update(sql,args);
            String sql1 = "select* from file where newName="+"'"+renameFileName+"'";
            SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql1);
            if(sqlRowSet.next()){
                Integer fid = sqlRowSet.getInt("id");
                String sql2 = "update assignment set fid ="+fid+" where id = "+"'"+id+"'";
                jdbcTemplate.update(sql2);
            }
            return"redirect:/depts";


        }
        @GetMapping("/download/{id}")
        public void download(@PathVariable("id")String id, HttpServletResponse response) throws IOException {
            String sql = "select * from file where id="+id;
            com.example.demo.Entity.File files = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<com.example.demo.Entity.File>(com.example.demo.Entity.File.class));
            String realPath = "e:/Employee/upload";
            FileInputStream fileInputStream = new FileInputStream(new File(realPath,files.getNewName()));
            response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(files.getOldName(),"UTF-8"));
            ServletOutputStream servletOutputStream = response.getOutputStream();
            IOUtils.copy(fileInputStream,servletOutputStream);
            IOUtils.closeQuietly(fileInputStream);
            IOUtils.closeQuietly(servletOutputStream);

        }
        @RequestMapping("/icon/{id}")
        public String toUploadIcon(@PathVariable("id")String id,Model model){
            model.addAttribute("id",id);
            return "upload1";
        }
        @RequestMapping("/uploadicon/{id}")
        public String uploadimg(Model model, MultipartFile file, HttpServletRequest request,@PathVariable("id")String id)
        {
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
                        String sql = "update emp set iconName = "+"'"+targetFileName+"'"+"where id = "+"'"+id+"'";
                        String sql1 =  "update community set iconName = "+"'"+targetFileName+"'"+"where eid = "+"'"+id+"'";
                        jdbcTemplate.update(sql);
                        jdbcTemplate.update(sql1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return "redirect:/mys";
                }
                model.addAttribute("tips","选择的文件不是图片，请重新选择！");
                return "upload1";
            }
            model.addAttribute("tips","未选择文件");
            return "upload1";
        }

        /*文件重命名方法*/
        public static String renameFileName(String fileName) {
            String formatDate = new SimpleDateFormat("yyMMddHHmmss").format(new Date()); // 当前时间字符串
            int random = new Random().nextInt(10000);
            String extension = fileName.substring(fileName.lastIndexOf(".")); // 文件后缀

            return formatDate + random + extension;
        }
    }

