package org.acme.service;

import org.acme.model.dto.UserBasicInfoResponse;
import org.acme.model.dto.UserRequest;

import java.util.List;

public interface UserService {

    List<UserBasicInfoResponse> userInfos();

    void createUser(UserRequest userResponse) ;
}
