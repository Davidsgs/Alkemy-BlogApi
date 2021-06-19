package com.alkemy.challenge.Blog.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Long id;

    private String title;

    private String content;

    private String image;

    public String category;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    public Date creationDate;

    public Long userId;
}
