package com.example.relationship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.relationship.model.Post;


@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{

}
