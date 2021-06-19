package com.alkemy.challenge.Blog.repository;

import com.alkemy.challenge.Blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    ArrayList<Post> findAllByOrderByCreationDateDesc();
    ArrayList<Post> findByTitleOrderByCreationDateDesc(String title);
    ArrayList<Post> findByCategoryOrderByCreationDateDesc(String category);
    ArrayList<Post> findByTitleAndCategoryOrderByCreationDateDesc(String title, String category);

}
