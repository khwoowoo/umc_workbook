package umc.UMC_WORKBOOK.repository;

import umc.UMC_WORKBOOK.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    boolean remove(int postID);
    boolean update(Post updatePost);
    Optional<Post> findByTitle(String title);
    Optional<Post> findById(int id);
    List<Post> findAll();
}
