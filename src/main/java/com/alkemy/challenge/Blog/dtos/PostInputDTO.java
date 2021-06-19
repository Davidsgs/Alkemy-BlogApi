package com.alkemy.challenge.Blog.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class PostInputDTO {

    private String title;
    private String content;
    private String image;
    public String category;
    public Date creationDate;
}
