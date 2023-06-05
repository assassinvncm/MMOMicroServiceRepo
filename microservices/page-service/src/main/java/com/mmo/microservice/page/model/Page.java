package com.mmo.microservice.page.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "r_page")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Page {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String fb_page_id;
    private String page_name;
    private Long u_fb_id;
    private Date created_date;
}
