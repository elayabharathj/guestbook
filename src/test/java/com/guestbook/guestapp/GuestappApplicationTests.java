package com.guestbook.guestapp;

import com.guestbook.guestapp.controller.IndexController;
import com.guestbook.guestapp.controller.PostController;
import com.guestbook.guestapp.model.Post;
import com.guestbook.guestapp.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class GuestappApplicationTests {

	@Autowired
	IndexController indexController;

	@Autowired
	PostController postController;

	@Autowired
	PostService postService;



	@Test
	void contextLoads() {
		assertThat(indexController).isNotNull();
		assertThat(postController).isNotNull();
		assertThat(postService).isNotNull();
	}


	//Uncomment when you need to delete all posts created for test.
	/*@Test
	public void deleteAllPosts(){
		Iterable<Post> allPosts = postService.getAllPost();
		allPosts.forEach(post -> postService.delete(post.getId()));

	}*/


	@Test
	public void createPost(){
		Post post = new Post();
		post.setApprovedStatus(false);
		post.setTitle("Title of the body goes here");
		post.setBody("This is Test body");
		File file = new File("C:/Elayabharath/Uploaded_image.jpg");
		post.setFile(file);
		post.setName("Uploaded_image.jpg");

		if(Objects.nonNull(post.getFile())){
			try{
				byte[] fileContent = Files.readAllBytes(post.getFile().toPath());
				post.setContent(fileContent);
			}
			catch (IOException ex){
			}
		}
		postService.save(post);
		assertThat(post).isNotNull();
		assertThat(post.getId()).isNotNull();
		Post postCreated = postService.getPostById(post.getId());
		assertThat(postCreated).isNotNull();
		assertThat(postCreated.getId()).isNotNull();
		assertThat(postCreated.getTitle()).isEqualTo(post.getTitle());
		assertThat(postCreated.getBody()).isEqualTo(post.getBody());
		assertThat(postCreated.getName()).isEqualTo("Uploaded_image.jpg");
		assertThat(postCreated.getContent()).isNotNull();

	}

	@Test
	public void approvePost(){
		Post post = new Post();
		post.setApprovedStatus(false);
		post.setTitle("This is Test post Approved by Admin");
		post.setBody("Admin has approved this request");
		postService.save(post);
		assertThat(post).isNotNull();
		assertThat(post.getId()).isNotNull();
		postService.approve(post);
		assertThat(post).isNotNull();
		assertThat(post.getId()).isNotNull();
		assertThat(post.getBody()).isEqualTo("Admin has approved this request");
		assertThat(post.getTitle()).isEqualTo("This is Test post Approved by Admin");
		assertThat(post.getApprovedStatus()).isEqualTo(true);

	}

	@Test
	public void deletePost(){
		Post post = new Post();
		post.setApprovedStatus(false);
		post.setTitle("This is Test post to be Deleted");
		post.setBody("Hey Admin, Delete this");
		postService.save(post);
		assertThat(post).isNotNull();
		assertThat(post.getId()).isNotNull();
		Integer postId = post.getId();
		postService.delete(post.getId());
		Post postDeleted = postService.getPostById(postId);
		assertThat(postDeleted).isNull();

	}

	@Test
	public void editPost(){
		Post post = new Post();
		post.setApprovedStatus(false);
		post.setTitle("This is Test post to be Updated");
		post.setBody("Hey Admin, Update this");
		postService.save(post);
		assertThat(post).isNotNull();
		assertThat(post.getId()).isNotNull();

		post.setBody("Admin has updated the content");

		Integer postId = post.getId();
		postService.save(post);
		Post postUpdated = postService.getPostById(postId);
		assertThat(postUpdated).isNotNull();
		assertThat(postUpdated.getBody()).isEqualTo("Admin has updated the content");
		assertThat(postUpdated.getBody()).isNotEqualTo("Hey Admin, Update this");

	}

	@Test
	public void getAllPosts(){
		Iterable<Post> allPosts = postService.getAllPost();
		assertThat(allPosts).isNotNull();
		assertThat(allPosts.iterator().next().getBody()).isNotNull();
		assertThat(allPosts.iterator().next().getTitle()).isNotNull();
		assertThat(allPosts.iterator().next().getId()).isNotNull();

	}

}
