package com.example.booking.mapper;

import com.example.booking.model.User;
import com.example.booking.web.model.UpsertUserRequest;
import com.example.booking.web.model.UserListResponse;
import com.example.booking.web.model.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

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
