package com.example.relationship.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.relationship.model.Post;
import com.example.relationship.repository.PostRepository;

@RestController
//@RequestMapping("/postApi")
public class PostController {

	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("/posts")
	public Page<Post> getAllPosts(Pageable pageable){
		return postRepository.findAll(pageable);
	}
	
	@PostMapping("/posts")
	public Post cretePost(@Valid @RequestBody Post post) {
		return postRepository.save(post);
	}
	@PutMapping("/posts/{postId}")
	public Post updatePost(@PathVariable Integer id,@Valid @RequestBody Post postRequest) {
		Optional<Post> findById = postRepository.findById(id);//.map(post ->{})
		return findById.map(post -> {
			post.setContent(postRequest.getContent());
			post.setDescription(postRequest.getDescription());
			post.setTitle(postRequest.getTitle());
			return postRepository.save(post);
		}).orElseThrow(() -> new ResourceNotFoundException("Post Id", String.valueOf(id), "Not found"));
	}
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable(value="postId") Integer id){
		return postRepository.findById(id)
		.map(post -> {
			postRepository.delete(post);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Post Id", String.valueOf(id), "Not found"));
	}
}
