package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Emp {
    private String id;
    private String name;
    private Integer sex;
    private String profession;
    private String phone;
    private String resume;
    private String birthday;
    private Integer balance;
    private String iconName;
}
