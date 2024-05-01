//package com.bc115.userservice.controller;
//
//import com.bc115.userservice.dto.UserDTO;
//import com.bc115.userservice.exception.UserException;
//import com.bc115.userservice.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/users")
//public class UserController {
//
//    private final UserService userService;
//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//    @GetMapping
//    public ResponseEntity<List<UserDTO>> getAllUsers() {
//        List<UserDTO> users = userService.getAllUsers();
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }
//
//    @GetMapping("/{email}")
//    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
//        try {
//            UserDTO userDTO = userService.getUserByEmail(email);
//            return new ResponseEntity<>(userDTO, HttpStatus.OK);
//        } catch (UserException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping
//    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
//        try {
//            UserDTO createdUser = userService.createUser(userDTO);
//            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
//        } catch (UserException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PatchMapping("/{id}")
//    public ResponseEntity<UserDTO> updateUserPassword(
//            @PathVariable int id,
//            @RequestBody UserDTO userDTO
//    ) {
//        try {
//            userDTO.setId(id);
//            UserDTO updatedUserDTO = userService.updateUserPassword(userDTO);
//            return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
//        } catch (UserException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//    @PutMapping("/{id}")
//    public ResponseEntity<UserDTO> updateBalance(
//            @PathVariable int id,
//            @RequestBody UserDTO userDTO) {
//        try {
//            userDTO.setId(id);
//            UserDTO updatedUser = userService.updateUserBalance(userDTO);
//            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
//        } catch (UserException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//}
package com.bc115.userservice.controller;

import com.bc115.userservice.dto.UserDTO;
import com.bc115.userservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.signUp(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestParam String email, @RequestParam String password) {
        UserDTO userDTO = userService.login(email, password);
        return ResponseEntity.ok(userDTO);
    }
}
