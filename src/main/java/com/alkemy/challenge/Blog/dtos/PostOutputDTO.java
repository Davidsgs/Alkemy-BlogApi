package com.alkemy.challenge.Blog.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class PostOutputDTO {
    private Long id;
    private String title;
    private String image;
    private String category;
    private Date creationDate;
}
