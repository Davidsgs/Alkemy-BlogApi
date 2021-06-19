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
    public ResponseEntity<PostOutputDTO> getPostById(@PathVariable Long id){
        var responseEntity = new ResponseEntity<PostOutputDTO>(HttpStatus.BAD_REQUEST);
        try{
            responseEntity = new ResponseEntity<>(postService.getDTOById(id),HttpStatus.OK);
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @PostMapping
    public ResponseEntity<PostOutputDTO> createPost(@RequestBody PostInputDTO post, @AuthenticationPrincipal MyUserDetails user){
        return new ResponseEntity<>(postService.createPost(post,user),HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<PostOutputDTO> updatePost(@PathVariable Long id, @RequestBody PostInputDTO post){
        var responseEntity = new ResponseEntity<PostOutputDTO>(HttpStatus.BAD_REQUEST);
        try{
            responseEntity = new ResponseEntity<>(postService.updatePost(id,post),HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return responseEntity;
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deletePost(@PathVariable Long id){
        var responseEntity = new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
        try{
            responseEntity = new ResponseEntity<>(postService.deletePost(id),HttpStatus.OK);
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }
}
