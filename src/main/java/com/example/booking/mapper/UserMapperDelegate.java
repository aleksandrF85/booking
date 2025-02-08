package com.example.booking.mapper;

import com.example.booking.model.User;
import com.example.booking.web.model.UpsertUserRequest;
import com.example.booking.web.model.UserResponse;

public abstract class UserMapperDelegate implements UserMapper{

    @Override
    public User requestToUser(UpsertUserRequest request) {

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return user;
    }

    @Override
    public User requestToUser(Long id, UpsertUserRequest request) {

        User user = requestToUser(request);
        user.setId(id);

        return user;
    }

    @Override
    public UserResponse userToResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());

        return response;
    }
}
