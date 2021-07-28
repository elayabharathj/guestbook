package com.guestbook.guestapp.controller;

import com.guestbook.guestapp.model.Post;
import com.guestbook.guestapp.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;

@Controller
public class PostController {

    private static Logger LOGGER = LoggerFactory.getLogger(PostController.class);
    private static String FILE_PATH = "C:/Elayabharath/";

    @Autowired
    PostService postService;

    /**
     * Method to get all the posts available
     * Only ROLE_ADMIN is authorized to view/edit/approve all the posts available
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/posts")
    public String allPost(Model model) {
        LOGGER.info("Entering allPost()");
        Iterable<Post> posts = postService.getAllPost();
        model.addAttribute("posts", posts);
        LOGGER.info("Leaving allPost()");
        return "all-post";
    }

    /**
     * This a dummy post request to map the model elements in the post creation form
     * Only ROLE_USER is authorized to create new posts
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/post/new")
    public String newPost(Model model) {
        LOGGER.info("Entering newPost()");
        Post post = new Post();
        model.addAttribute("post", new Post());
        model.addAttribute("action","post");
        LOGGER.info("Leaving newPost()");
        return "post";
    }

    /**
     * Method to return the post values to display in form for editing
     * Only ROLE_ADMIN is authorized to view/edit/approve all the posts available
     *
     * @param id
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/post/{id}/edit")
    public String editPost(@PathVariable Integer id, Model model) {
        LOGGER.info("Entering editPost() for postId {}", id);
        model.addAttribute("action","put");
        model.addAttribute("post", postService.getPostById(id));
        LOGGER.info("Leaving editPost() for postId {}", id);
        return "post";
    }

    /**
     * Method to return the delete post based on id
     * Only ROLE_ADMIN is authorized to view/edit/approve all the posts available
     * @param id
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/post/{id}/delete")
    public String deletePost(@PathVariable Integer id, Model model) {
        LOGGER.info("Entering deletePost() for postId {}", id);
        postService.delete(id);
        LOGGER.info("Leaving deletePost() for postId {}", id);
        return "redirect:/posts";
    }

    /**
     * Method to create new post. Map the form elements to model and persist
     * @param post
     * @param model
     * @return
     */
    @PostMapping("/post")
    public String newPost(@ModelAttribute Post post, Model model) {
        LOGGER.info("Entering newPost() {}", post.getId());
        if(Objects.nonNull(post.getFile()) && !StringUtils.isEmpty(post.getFile().getName())){
            LOGGER.info("FileName {}", post.getFile().getName());
            try{
                byte[] fileContent = Files.readAllBytes(Paths.get(FILE_PATH + post.getFile().getName()));
                LOGGER.info("fileContent.size {}", fileContent.length);
                post.setContent(fileContent);
                post.setName(post.getFile().getName());
            }
            catch (IOException ex){
                LOGGER.error("IOException Exception occured {}, ----- {}", ex.getMessage());
            }
        }
        String redirectTo = "success" ;
        if (validateUserPost(post)) return "error";
        //Request comes from the form page and also from the edit page.
        //Redirect is based on the id values which tells where the request comes from
        if(Objects.nonNull(post.getId()) && post.getId().intValue()!=0)  {
            redirectTo ="redirect:/posts";
        }
        LOGGER.info("redirectTo {}", redirectTo);
        postService.save(post);
        LOGGER.info("Leaving newPost() with postId created - {}", post.getId());
        return redirectTo;
    }

    /**
     * Method to update post. Map the form elements to model and persist.
     * Only ROLE_ADMIN is authorized to view/edit/approve all the posts available
     * @param post
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/post")
    public String updatePost(@ModelAttribute Post post, Model model) {
        LOGGER.info("Entering updatePost() {}", post.getId());
        if (validateUserPost(post)) return "error";
        postService.save(post);
        LOGGER.info("Leaving updatePost() {}", post.getId());
        return "all-post";
    }

    private boolean validateUserPost(@ModelAttribute Post post) {
        if (StringUtils.isEmpty(post.getTitle())|| (StringUtils.isEmpty(post.getBody()))){
            return true;
        }
        return false;
    }

    /**
     * Method to approve post. Map the form elements to model and persist with approvedstatus true.
     * Only ROLE_ADMIN is authorized to view/edit/approve all the posts available
     * @param id
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/post/{id}/approve")
    public String approvePost(@PathVariable Integer id, Model model) {
        LOGGER.info("Entering approvePost() with post id {}", id);
        Post post = postService.getPostById(id);
        postService.approve(post);
        LOGGER.info("Leaving approvePost() for post id {}", id);
        return "redirect:/posts";
    }

    /**
     * Method to approve post. Map the form elements to model and persist with approvedstatus true.
     * Only ROLE_ADMIN is authorized to view/edit/approve all the posts available
     * @param id
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/post/{id}/view")
    public String viewUploadedImage(@PathVariable Integer id, Model model) {
        LOGGER.info("Entering viewPost() with post id {}", id);
        Post post = postService.getPostById(id);
        LOGGER.info("post content size {}", Objects.nonNull(post.getContent()) ? post.getContent().length: 0);
        post.setDownload(Base64.getEncoder().encodeToString(post.getContent()));
        model.addAttribute("post", post);
        return "post";
    }
}
