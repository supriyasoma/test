//package com.bc115.userservice.service;
//
//import com.bc115.userservice.dto.UserDTO;
//import com.bc115.userservice.entity.User;
//import com.bc115.userservice.exception.UserException;
//import com.bc115.userservice.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//@Service
//public class UserServiceImpl implements UserService {
//
//    private final UserRepository userRepository;
//
//    @Autowired
//    public UserServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public List<UserDTO> getAllUsers() {
//        List<User> users = userRepository.findAll();
//        return users.stream()
//                .map(UserDTO::mapToDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public UserDTO createUser(UserDTO userDTO) {
//        try {
//            Optional<User> userOptional = userRepository.findByEmail(userDTO.getEmail());
//            if (userOptional.isPresent()) {
//                throw new UserException("Email is already exists!");
//            }
//            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//            User user = UserDTO.mapToEntity(userDTO);
//            User savedUser = userRepository.save(user);
//            return UserDTO.mapToDTO(savedUser);
//        } catch(UserException e) {
//            throw new UserException("Error creating user");
//        }
//    }
//
//    @Override
//    public UserDTO updateUserPassword(UserDTO userDTO) {
//        Optional<User> optionalUser = userRepository.findById(userDTO.getId());
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            String hashedNewPassword = passwordEncoder.encode(userDTO.getPassword());
//
//            if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
//                user.setPassword(hashedNewPassword);
//                userRepository.save(user);
//                return UserDTO.mapToDTO(user);
//            }
//        }
//        throw new UserException("Error updating user password");
//    }
//
//    @Override
//    public UserDTO getUserByEmail(String email) {
//        Optional<User> optionalUser = userRepository.findByEmail(email);
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            return UserDTO.mapToDTO(user);
//        } else {
//            throw new UserException("User with email " + email + " not found.");
//        }
//    }
//}

package com.bc115.userservice.service;

import com.bc115.userservice.dto.UserDTO;
import com.bc115.userservice.entity.User;
import com.bc115.userservice.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserDTO signUp(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO login(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user == null) {
            throw new RuntimeException("Invalid email or password");
        }
        return modelMapper.map(user, UserDTO.class);
    }
}
