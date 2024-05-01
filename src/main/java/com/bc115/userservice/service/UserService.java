package com.bc115.userservice.service;

import com.bc115.userservice.dto.UserDTO;

import java.util.List;

//public interface UserService {
//    List<UserDTO> getAllUsers();
//
//    UserDTO getUserByEmail(String email);
//
//    UserDTO createUser(UserDTO userDTO);
//
//    UserDTO updateUserPassword(UserDTO userDTO);
//
//
//}

public interface UserService {
    UserDTO signUp(UserDTO userDTO);
    UserDTO login(String email, String password);
}
