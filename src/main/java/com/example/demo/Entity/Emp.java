package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Emp {
    private Integer id;
    private String name;
    private Integer sex;
    private String birth;
    private String phone;
    private String deptId;
    private String idcard;
    private String deptName;
}
