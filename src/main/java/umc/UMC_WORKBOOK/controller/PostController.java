package umc.UMC_WORKBOOK.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import umc.UMC_WORKBOOK.domain.Post;
import umc.UMC_WORKBOOK.repository.PostRepository;
import umc.UMC_WORKBOOK.service.PostService;

import java.util.Date;

@Controller
public class PostController {
    private PostService postService;

    @Autowired
    public  PostController(PostRepository postRepository){
        postService = new PostService(postRepository);
    }

    @GetMapping("/post/new")
    public String formPage(Model model){
        model.addAttribute("user",  LoginUser.getInstance().GetLoginUser());
        return "Post/createPostForm";
    }


    @PostMapping("/post/new")
    public String createPost(@Valid @ModelAttribute("PostForm") PostForm form, BindingResult result, Model model) {
        if(result.hasErrors()){
            model.addAttribute("obj", LoginUser.getInstance().GetLoginUser().getName());
            return "user";
        }

        Post post = new Post();
        post.setUserId(form.getUserId());
        post.setTitle(form.getTitle());
        post.setContent(form.getContent());
        post.setPostDate(new Date());
        postService.create(post);
        model.addAttribute("obj", LoginUser.getInstance().GetLoginUser().getName());
        return "user";
    }


    @GetMapping("/postList")
    public String ListPage(Model model){
        model.addAttribute("list", postService.findPosts());
        return "Post/postList";
    }

    @GetMapping("/postView/{id}")
    public String viewPage(@PathVariable("id") int id, Model model){
        model.addAttribute("obj", postService.findOne(id).get());
        model.addAttribute("isWriter",
                (postService.findOne(id).get().getUserId().equals(LoginUser.getInstance().GetLoginUser().getId())));
        return  "Post/postView";
    }


    @GetMapping("/post/edit")
    public String editPage(@RequestParam("id") int id, Model model){
        model.addAttribute("obj", postService.findOne(id).get());
        model.addAttribute("postID", id);
        return  "Post/postEdit";
    }

    @PostMapping("/post/update")
    public String updatePost(PostForm form, Model model){
        Post post = postService.findOne(Integer.parseInt(form.getPostId())).get();
        post.setTitle(form.getTitle());
        post.setContent(form.getContent());
        post.setPostDate(new Date());
        model.addAttribute("obj", post);
        model.addAttribute("isWriter", true);
        postService.update(post);
        return "Post/postView";
    }

    @PostMapping("/post/delete")
    public String removePost(@RequestParam("id") int id){
        postService.remove(id);

        return "redirect:/postList";
    }


}
