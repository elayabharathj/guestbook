package com.guestbook.guestapp.service;

import com.guestbook.guestapp.model.Post;
import com.guestbook.guestapp.repository.PostRepository;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.NoSuchElementException;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public Iterable<Post> getAllPost() {
        return postRepository.findAll();
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public void delete(Integer id) {
        postRepository.deleteById(id);
    }

    public void approve(Post post) {
        post.setApprovedStatus(true);
        postRepository.save(post);
    }

    public Post getPostById(Integer id){
        try{
            return postRepository.findById(id).get();
        }catch(NoSuchElementException ex){
            return null;
        }

    }

}
