package org.acme.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.common.exceptions.ResourceNotFound;
import org.acme.common.util.PasswordUtil;
import org.acme.model.dto.UserBasicInfoResponse;
import org.acme.model.dto.UserRequest;
import org.acme.model.entities.Role;
import org.acme.model.entities.User;
import org.acme.service.UserService;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Override
    public UserBasicInfoResponse getUserInfoByEmail(String email) {
        return User.getUserBasicInfoByEmail(email);
    }

    @Override
    public List<UserBasicInfoResponse> userInfos() {
        return User.getUsersBasicInfo();
    }

    @Override
    @Transactional
    public void createUser(UserRequest userResponse) {
        User user = User.findByEmail(userResponse.email);
        if (user != null) {
            throw new ResourceNotFound("user already exists!");
        }
        Role role = Role.findByName(userResponse.role);
        user = new User();
        user.firstName = userResponse.firstName;
        user.lastName = userResponse.lastName;
        user.email = userResponse.email;
        user.isActive = true;
        user.password = PasswordUtil.hashPassword(userResponse.password);
        user.roles = Set.of(role);
        user.persist();
    }

    @Override
    @Transactional
    public void deactivateUser(String email) {
        User user = User.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFound("User not found");
        }
        user.isActive = false;
        user.persist();
    }

    @Override
    @Transactional
    public void activateUser(String email) {
        User user = User.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFound("User not found");
        }
        if (user.isActive) {
            throw new ResourceNotFound("User already active");
        }
        user.isActive = true;
        user.persist();
    }

    @Override
    @Transactional
    public boolean isUserValid(String username) {
        return User.findByEmail(username)!=null;
    }

}
