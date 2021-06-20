package com.alkemy.challenge.Blog.models;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "posts")
@SQLDelete(sql = "UPDATE posts SET deleted=true WHERE id = ?")
@Where(clause = "deleted = false")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String title;

    private String content;

    private String image;

    public String category;

    public Date creationDate;

    public Long userId;

    private Boolean deleted = false;
}
