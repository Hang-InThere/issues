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
public class Assignment {
    private Integer id;
    private String name;
    private String startTime;
    private String deadLine;
    private Integer money;
    private String resume;
}
