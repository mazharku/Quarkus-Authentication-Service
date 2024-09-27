package org.acme.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.model.dto.UserBasicInfoResponse;
import org.acme.model.dto.UserRequest;
import org.acme.model.entities.Role;
import org.acme.model.entities.User;
import org.acme.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Override
    public List<UserBasicInfoResponse> userInfos() {
        List<User> users = User.listAll();
        return  users.stream()
                .map(e-> {
                    UserBasicInfoResponse infoResponse = new UserBasicInfoResponse();
                    infoResponse.fullName = e.firstName+" "+e.lastName;
                    infoResponse.email = e.email;
                    infoResponse.roleName = e.roles.stream().map(e1-> e1.name).collect(Collectors.joining());
                    return infoResponse;
                }).toList();
    }

    @Override
    @Transactional
    public void createUser(UserRequest userResponse) {
        User user = User.findByEmail(userResponse.email);
        if(user!=null) {
            throw new RuntimeException("user already exists!");
        }
        Role role = Role.findByName(userResponse.role);
        user = new User();
        user.firstName = userResponse.firstName;
        user.lastName = userResponse.lastName;
        user.email = userResponse.email;
        user.isActive = true;
        user.password = userResponse.password;
        user.roles = Set.of(role);
        user.persist();
    }

}
