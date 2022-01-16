package com.springboot.mini.project.restfulwebservices.dao;

import com.springboot.mini.project.restfulwebservices.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
