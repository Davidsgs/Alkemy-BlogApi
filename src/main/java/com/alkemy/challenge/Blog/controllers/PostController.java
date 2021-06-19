package com.alkemy.challenge.Blog.controllers;

import com.alkemy.challenge.Blog.dtos.PostInputDTO;
import com.alkemy.challenge.Blog.dtos.PostOutputDTO;
import com.alkemy.challenge.Blog.models.MyUserDetails;
import com.alkemy.challenge.Blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/posts")
public class PostController {

    @Autowired
    public PostService postService;

    @GetMapping
    public ResponseEntity<List<PostOutputDTO>> getPosts(@RequestParam(required = false) String title,
                                                        @RequestParam(required = false) String category) {
        var responseEntity = new ResponseEntity<List<PostOutputDTO>>(HttpStatus.BAD_REQUEST);
        if (title != null && category != null) {
            responseEntity = new ResponseEntity<>(postService.filterByTitleAndCategory(title, category), HttpStatus.OK);
        } else if (title != null) {
            responseEntity = new ResponseEntity<>(postService.filterByTitle(title), HttpStatus.OK);
        } else if (category != null) {
            responseEntity = new ResponseEntity<>(postService.filterByCategory(category), HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(postService.getAllDTOPosts(), HttpStatus.OK);
        }
        return responseEntity;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity getPostById(@PathVariable Long id) {
        ResponseEntity responseEntity;
        try {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(postService.getDTOById(id));
        } catch (Exception e) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
        return responseEntity;
    }

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostInputDTO post, @AuthenticationPrincipal MyUserDetails user) {
        return new ResponseEntity(postService.createPost(post, user), HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity updatePost(@PathVariable Long id, @RequestBody PostInputDTO post) {
        ResponseEntity responseEntity;
        try {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(id, post));
        } catch (Exception e) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
        return responseEntity;
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deletePost(@PathVariable Long id) {
        ResponseEntity responseEntity;
        try {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(postService.deletePost(id));
        } catch (Exception e) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
        return responseEntity;
    }
}
