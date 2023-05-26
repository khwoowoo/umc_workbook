package umc.UMC_WORKBOOK.service;

import umc.UMC_WORKBOOK.domain.Post;
import umc.UMC_WORKBOOK.domain.User;
import umc.UMC_WORKBOOK.repository.PostRepository;
import umc.UMC_WORKBOOK.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void create(Post post){
        postRepository.save(post);
    }

    public void remove(int postID){
        postRepository.remove(postID);
    }

    public void update(Post editPost){
        postRepository.update(editPost);
    }

    public List<Post> findPosts(){
        return postRepository.findAll();
    }

    public Optional<Post> findOne(String title){
        return postRepository.findByTitle(title);
    }
    public Optional<Post> findOne(int id){
        return postRepository.findById(id);
    }
}
