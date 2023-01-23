package com.alimurat.SplitWise.controller;

import com.alimurat.SplitWise.dto.SaveUserRequest;
import com.alimurat.SplitWise.dto.UserResponse;
import com.alimurat.SplitWise.model.User;
import com.alimurat.SplitWise.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/details/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping
    public ResponseEntity<UserResponse> saveUser(@RequestBody SaveUserRequest request){
        return ResponseEntity.ok(userService.saveUser(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Integer id, @RequestBody SaveUserRequest request){
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id){
        userService.deleteUserById(id);
    }

    @PatchMapping("/{id}")
    public void deactivateUser(@PathVariable Integer id){
        userService.deactivateUser(id);
    }

    @PatchMapping("/activate/{id}")
    public void activateUser(@PathVariable Integer id){
        userService.activateUser(id);
    }
}
