package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component

public class Community {
    private Integer id;
    private String ename;
    private String iconName;
    private String text;
    private String picture;
    private String eid;
    private String time;
}
