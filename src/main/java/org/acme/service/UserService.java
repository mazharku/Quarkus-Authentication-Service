package org.acme.service;

import org.acme.model.dto.UserBasicInfoResponse;
import org.acme.model.dto.UserRequest;

import java.util.List;

public interface UserService {

    UserBasicInfoResponse getUserInfoByEmail(String email);
    List<UserBasicInfoResponse> userInfos();

    void createUser(UserRequest userResponse) ;

    void deactivateUser(String email);

    void activateUser(String email);

    boolean isUserValid(String username);
}
