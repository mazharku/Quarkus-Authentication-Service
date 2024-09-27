package org.acme.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.model.dto.UserBasicInfoResponse;
import org.acme.model.dto.UserRequest;
import org.acme.model.entities.Role;
import org.acme.model.entities.User;
import org.acme.service.UserService;

import java.util.List;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Override
    public List<UserBasicInfoResponse> userInfos() {
        return User.getUsersBasicInfo();
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
        //user.roles = Set.of(role);
        user.persist();
    }

}
