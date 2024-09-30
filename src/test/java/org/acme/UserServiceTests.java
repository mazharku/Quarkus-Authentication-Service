package org.acme;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.common.exceptions.ResourceNotFound;
import org.acme.model.dto.UserRequest;
import org.acme.model.entities.Role;
import org.acme.model.entities.User;
import org.acme.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
public class UserServiceTests {
    @Inject
    UserService userService;

    @Test
    void test__createUser_userAlreadyExists_throwException(){
        PanacheMock.mock(User.class);
        PanacheMock.mock(Role.class);
        String email = "abc@test.com";
        String role = "User";

        UserRequest userRequest = new UserRequest();
        userRequest.email= email;
        userRequest.firstName="ab";
        userRequest.lastName="c";
        userRequest.password="123456";
        userRequest.role= role;

        Mockito.when(User.findByEmail(email)).thenReturn(new User());
        Mockito.when(Role.findByName(role)).thenReturn(new Role());

        Assertions.assertThrows(ResourceNotFound.class, ()-> userService.createUser(userRequest));
    }

    @Test
    void test__createUser_roleNotExists_throwException(){
        PanacheMock.mock(User.class);
        PanacheMock.mock(Role.class);
        String email = "abc@test.com";
        String role = "User";

        UserRequest userRequest = new UserRequest();
        userRequest.email= email;
        userRequest.firstName="ab";
        userRequest.lastName="c";
        userRequest.password="123456";
        userRequest.role= role;

        Mockito.when(User.findByEmail(email)).thenReturn(new User());
        Mockito.when(Role.findByName(role)).thenReturn(null);

        Assertions.assertThrows(ResourceNotFound.class, ()-> userService.createUser(userRequest));
    }

    @Test
    void test__createUser_success(){
        PanacheMock.mock(User.class);
        PanacheMock.mock(Role.class);
        String email = "abc@test.com";
        String role = "User";

        UserRequest userRequest = new UserRequest();
        userRequest.email= email;
        userRequest.firstName="ab";
        userRequest.lastName="c";
        userRequest.password="123456";
        userRequest.role= role;
        Role role1 = new Role();


        Mockito.when(User.findByEmail(email)).thenReturn(null);
        Mockito.when(Role.findByName(role)).thenReturn(role1);

        userService.createUser(userRequest);

    }
}
