package com.example.relationship.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.relationship.model.Comment;
import com.example.relationship.repository.CommentRepository;
import com.example.relationship.repository.PostRepository;

@RestController
public class CommentController {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("/posts/{id}/comments")
	public Page<Comment> getAllCommentsByPostId(@PathVariable Integer id, Pageable pageable){
		return commentRepository.findByPostId(id, pageable);
	}
	
	@PostMapping("/posts/{id}/comments")
	public Comment createComment(@PathVariable Integer id, @Valid @RequestBody Comment comment) {
		return postRepository.findById(id).map(post -> {
			comment.setPost(post);
			return commentRepository.save(comment);
		}).orElseThrow(() -> new ResourceNotFoundException("Post id", String.valueOf(id), " No found"));
	}
	
	@PostMapping("/posts/{postId}/comments/{commentId}")
	public Comment updateComment(@PathVariable(value="postId") Integer postId, @PathVariable(value="commentId") Integer commentId, @Valid Comment commentRequest) {
		if(!postRepository.existsById(postId)) {
			throw new ResourceNotFoundException("Post Id", String.valueOf(postId), "Not found");
		}
		return commentRepository.findById(commentId).map(comment  -> {
			comment.setText(commentRequest.getText());
			return commentRepository.save(comment);
		}).orElseThrow(() -> new ResourceNotFoundException("Comment Id", String.valueOf(commentId), "Not found"));
	}
	
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable(value="postId") Integer postId, @PathVariable(value="commentId") Integer commentId){
		return commentRepository.findByIdAndPost(commentId, postId).map(comment -> {
			commentRepository.delete(comment);
			return ResponseEntity.ok().build();
		}).orElseThrow(() ->new ResourceNotFoundException("Comment Id", String.valueOf(commentId), "Not found"));
	}
	
}
