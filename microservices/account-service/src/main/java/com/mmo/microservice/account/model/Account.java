package com.mmo.microservice.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "r_fb_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String fb_id;
    private String username;
    private String pass;
    private String type;
    private long r_user_id;
    private Date created_date;
}
