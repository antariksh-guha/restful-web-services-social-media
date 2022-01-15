package com.springboot.mini.project.restfulwebservices.controller;

import com.springboot.mini.project.restfulwebservices.dao.UserDAOService;
import com.springboot.mini.project.restfulwebservices.exception.UserNotFoundException;
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

@RestController
public class AppController {

    @Autowired
    private UserDAOService userDAOService;

    @GetMapping(path = "/users")
    public List<User> retrieveAllUsers()
    {
        return userDAOService.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public EntityModel<User> retrieveSpecificUser(@PathVariable int id) {
        User user = userDAOService.findById(id);
        if(user==null)
            throw new UserNotFoundException("id = "+id);

        EntityModel<User> resource = EntityModel.of(user);

        WebMvcLinkBuilder linkTo =
                linkTo(methodOn(this.getClass()).retrieveAllUsers());

        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<Object> retrieveSpecificUser(@Valid @RequestBody User user) {
        User savedUser = userDAOService.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteSpecificUser(@PathVariable int id) {
        User user = userDAOService.deleteById(id);
        if(user==null)
            throw new UserNotFoundException("id = "+id);
    }
}
