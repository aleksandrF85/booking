package com.example.booking.mapper;

import com.example.booking.model.User;
import com.example.booking.web.dto.UpsertUserRequest;
import com.example.booking.web.dto.UserListResponse;
import com.example.booking.web.dto.UserResponse;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(UserMapperDelegate.class)
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User requestToUser(UpsertUserRequest request);

    User requestToUser(Long id, UpsertUserRequest request);

    UserResponse userToResponse(User user);

    default UserListResponse userListToUserListResponse(List<User> users) {
        UserListResponse response = new UserListResponse();

        response.setUsers(users.stream()
                .map(this::userToResponse).collect(Collectors.toList()));

        return response;
    }

}
