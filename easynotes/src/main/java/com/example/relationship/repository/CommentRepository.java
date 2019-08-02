package com.example.relationship.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.relationship.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	Page<Comment> findByPostId(Integer postId, Pageable pageable);
	Optional<Comment> findByIdAndPost(Integer id, Integer postId);
}
