package com.alkemy.challenge.Blog.dtos;
import lombok.Data;
@Data
public class PostInputDTO {
    private String title;
    private String content;
    private String image;
    public String category;
}
