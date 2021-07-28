package com.guestbook.guestapp.repository;

import com.guestbook.guestapp.model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {
    //Implementation has been provided by the class SimpleJpaRepository of Springframwwork data
    //All dao calls are accessed through SimpleJpaRepository

}
