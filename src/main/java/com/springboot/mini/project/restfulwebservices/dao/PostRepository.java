package com.springboot.mini.project.restfulwebservices.dao;

import com.springboot.mini.project.restfulwebservices.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
