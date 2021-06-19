package com.alkemy.challenge.Blog.services;
import com.alkemy.challenge.Blog.dtos.PostInputDTO;
import com.alkemy.challenge.Blog.dtos.PostOutputDTO;
import com.alkemy.challenge.Blog.models.MyUserDetails;
import com.alkemy.challenge.Blog.models.Post;
import com.alkemy.challenge.Blog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;

@Service
public class PostService {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    //getFromId

    public Post getById(Long id){
        return postRepository.findById(id).orElseThrow(() -> new IllegalStateException("Doesn't exist a post with id: " + id));
    }

    public PostOutputDTO getDTOById(Long id) throws IllegalArgumentException{
        return modelMapper.map(getById(id),PostOutputDTO.class);
    }

    //getAll

    public ArrayList<PostOutputDTO> getAllDTOPosts() {
        return mapToOutputDTOS(postRepository.findAllByOrderByCreationDateDesc());
    }

    //createPost

    public PostOutputDTO createPost(PostInputDTO postDTO, MyUserDetails myUserDetails){
        var post = modelMapper.map(postDTO,Post.class);
        var user = userService.findByEmail(myUserDetails.getUsername());
        post.setUserId(user.getId());
        post.setCreationDate(new Date(System.currentTimeMillis()));
        postRepository.save(post);
        return modelMapper.map(post,PostOutputDTO.class);
    }

    //updatePost
    @Transactional
    public PostOutputDTO updatePost(Long id, PostInputDTO postDTO) {
        var postToUpdate = getById(id);
        if(postDTO.getTitle() != null) {postToUpdate.setTitle(postDTO.getTitle());}
        if(postDTO.getCategory() != null) {postToUpdate.setCategory(postDTO.getCategory());}
        if(postDTO.getContent() != null) {postToUpdate.setContent(postDTO.getContent());}
        if(postDTO.getImage() != null) {postToUpdate.setImage(postDTO.getImage());}
        return modelMapper.map(postToUpdate,PostOutputDTO.class);

    }

    //deletePost

    public boolean deletePost(Long id) {
        if(id == null){
            throw new IllegalArgumentException();
        }else if(getById(id) != null){
            postRepository.delete(getById(id));
            return true;
        }
        return false;
    }

    //filterByTitle & filterByCategory.

    public ArrayList<PostOutputDTO> filterByTitleAndCategory(String title, String category) {
        return mapToOutputDTOS(postRepository.findByTitleAndCategoryOrderByCreationDateDesc(title,category));
    }

    public ArrayList<PostOutputDTO> filterByTitle(String title) {
        return mapToOutputDTOS(postRepository.findByTitleOrderByCreationDateDesc(title));
    }

    public ArrayList<PostOutputDTO> filterByCategory(String category) {
        return mapToOutputDTOS(postRepository.findByCategoryOrderByCreationDateDesc(category));
    }

    //Aux Fuctions.

    public ArrayList<PostOutputDTO> mapToOutputDTOS(Iterable<Post> posts){
        var postOutputList = new ArrayList<PostOutputDTO>();
        for(Post post : posts){
            var auxDTO = new PostOutputDTO();
            modelMapper.map(post,auxDTO);
            postOutputList.add(auxDTO);
        }
        return postOutputList;
    }



}
