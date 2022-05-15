package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Account {
    private String eid;
    private String account;
    private String password;
    /*
        1表示自由职业者，0表示员工
     */
    private Integer identity;
}
