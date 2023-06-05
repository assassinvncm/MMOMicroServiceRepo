package com.mmo.microservice.token.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "r_token")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String type;
    private String token;
    private Date valid_from;
    private Date valid_to;
    private Date created_date;

    @Column(nullable = true)
    private Long r_page_id;

    @Column(nullable = true)
    private Long r_user_fb_id;
}
