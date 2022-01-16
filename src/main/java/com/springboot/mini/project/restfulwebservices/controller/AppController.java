package com.springboot.mini.project.restfulwebservices.controller;

import com.springboot.mini.project.restfulwebservices.dao.PostRepository;
import com.springboot.mini.project.restfulwebservices.dao.UserDAOService;
import com.springboot.mini.project.restfulwebservices.dao.UserRepository;
import com.springboot.mini.project.restfulwebservices.exception.UserNotFoundException;
import com.springboot.mini.project.restfulwebservices.model.Post;
import com.springboot.mini.project.restfulwebservices.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class AppController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping(path = "/users")
    public List<User> retrieveAllUsers()
    {
        List<User> user = userRepository.findAll();
//        //filtering
//        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name");
//        FilterProvider filters=new SimpleFilterProvider().addFilter("UserFilter", filter);
//        MappingJacksonValue mapping =new MappingJacksonValue(user);
//        mapping.setFilters(filters);
//        return mapping;
        return user;
    }

    @GetMapping(path = "/users/{id}")
    public EntityModel<User> retrieveSpecificUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent())
            throw new UserNotFoundException("id = "+id);
        // hateoas
        EntityModel<User> resource = EntityModel.of(user.get());
        WebMvcLinkBuilder linkTo =
                linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkTo.withRel("all-users"));
        return resource;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<Object> retrieveSpecificUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteSpecificUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @GetMapping(path = "/users/{id}/posts")
    public List<Post> retrieveSpecificUserPosts(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent())
            throw new UserNotFoundException("id = "+id);
        return user.get().getPosts();
    }

    @PostMapping(path = "/users/{id}/posts")
    public ResponseEntity<Object> retrieveSpecificUser(@PathVariable int id, @RequestBody Post post) {
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent())
            throw new UserNotFoundException("id = "+id);
        User user = userOptional.get();
        post.setUser(user);
        postRepository.save(post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
