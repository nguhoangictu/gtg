package com.htngu.gtg.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "post_detail")
public class PostDetail{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "content")
    private String content;
    @Column(name = "post_type")
    private String postType;
    @Column(name = "cost")
    private String cost;
    @Column(name = "images")
    private String images;
}
