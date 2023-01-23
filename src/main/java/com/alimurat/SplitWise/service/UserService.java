package com.alimurat.SplitWise.service;

import com.alimurat.SplitWise.dto.SaveUserRequest;
import com.alimurat.SplitWise.dto.UserResponse;
import com.alimurat.SplitWise.model.Role;
import com.alimurat.SplitWise.model.User;
import com.alimurat.SplitWise.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse getUserById(Integer id){
        User user = userRepository.findById(id).orElseThrow(RuntimeException::new);

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .password(user.getPassword())
                .isActive(user.getIsActive())
                .role(user.getRole())
                .house(user.getHouse())
                .expenses(user.getExpenses())
                .todos(user.getTodos())
                .build();
    }

    public List<User> getUsers(){
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);

        return users;
    }

    public UserResponse saveUser(SaveUserRequest request){

        User user = User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        User fromDb = userRepository.save(user);

        return UserResponse.builder()
                .id(fromDb.getId())
                .name(fromDb.getName())
                .surname(fromDb.getSurname())
                .email(fromDb.getEmail())
                .password(fromDb.getPassword())
                .isActive(true)
                .role(Role.USER)
                .house(fromDb.getHouse())
                .expenses(fromDb.getExpenses())
                .todos(fromDb.getTodos())
                .build();
    }

    public UserResponse updateUser(Integer id, SaveUserRequest request){

        User currentUser = userRepository.findById(id).orElseThrow(RuntimeException::new);

        if(!currentUser.getIsActive()){
            throw new RuntimeException("The user is not active");
        }

        currentUser.setName(request.getName());
        currentUser.setSurname(request.getSurname());
        currentUser.setEmail(request.getEmail());
        currentUser.setPassword(request.getPassword());

        userRepository.save(currentUser);

        return UserResponse.builder()
                .id(currentUser.getId())
                .name(currentUser.getName())
                .surname(currentUser.getSurname())
                .email(currentUser.getEmail())
                .password(currentUser.getPassword())
                .isActive(true)
                .role(currentUser.getRole())
                .house(currentUser.getHouse())
                .expenses(currentUser.getExpenses())
                .todos(currentUser.getTodos())
                .build();
    }

    public void deleteUserById(Integer id){
        if(doesUserExist(id)){
            userRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("User does not exist");
        }
    }
    public void deactivateUser(Integer id){
        changeUserStatus(id, false);
    }

    public void activateUser(Integer id){
        changeUserStatus(id, true);
    }

    private void changeUserStatus(Integer id, Boolean isActive){
        User currentUser = userRepository.findById(id).orElseThrow(RuntimeException::new);

        currentUser.setIsActive(isActive);
        userRepository.save(currentUser);
    }

    public User getUserByEmail(String email){

        return userRepository.findByEmail(email);
    }

    private boolean doesUserExist(Integer id){
        return userRepository.existsById(id);
    }
}
