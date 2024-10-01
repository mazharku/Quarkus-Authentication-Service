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
    void test__createUser_userAlreadyExists_throwException() {
        PanacheMock.mock(User.class);
        PanacheMock.mock(Role.class);
        String email = "abc@test.com";
        String role = "User";

        UserRequest userRequest = new UserRequest();
        userRequest.email = email;
        userRequest.firstName = "ab";
        userRequest.lastName = "c";
        userRequest.password = "123456";
        userRequest.role = role;

        PanacheMock.doReturn(new User()).when(User.class).findByEmail(email);
        PanacheMock.doReturn(new Role()).when(Role.class).findByName(role);

        Assertions.assertThrows(ResourceNotFound.class, () -> userService.createUser(userRequest));

    }

    @Test
    void test__createUser_roleNotExists_throwException() {
        PanacheMock.mock(User.class);
        PanacheMock.mock(Role.class);
        String email = "abc@test.com";
        String role = "User";

        UserRequest userRequest = new UserRequest();
        userRequest.email = email;
        userRequest.firstName = "ab";
        userRequest.lastName = "c";
        userRequest.password = "123456";
        userRequest.role = role;

        PanacheMock.doReturn(new User()).when(User.class).findByEmail(email);
        PanacheMock.doReturn(null).when(Role.class).findByName(role);

        Assertions.assertThrows(ResourceNotFound.class, () -> userService.createUser(userRequest));
    }

    @Test
    void test__createUser_success() {
        PanacheMock.mock(User.class);
        PanacheMock.mock(Role.class);
        String email = "abc@test.com";
        String role = "User";

        UserRequest userRequest = new UserRequest();
        userRequest.email = email;
        userRequest.firstName = "ab";
        userRequest.lastName = "c";
        userRequest.password = "123456";
        userRequest.role = role;

        User user = Mockito.mock(User.class);


        PanacheMock.doReturn(null).when(User.class).findByEmail(email);
        PanacheMock.doReturn(new Role()).when(Role.class).findByName(role);
        Mockito.doNothing().when(user).persist();

        userService.createUser(userRequest);

        PanacheMock.verify(User.class, Mockito.times(1)).findByEmail(email);
        PanacheMock.verify(Role.class, Mockito.times(1)).findByName(role);
    }

    @Test
    void test__deactivateUser_userNotExists_throwsException() {
        PanacheMock.mock(User.class);
        String email = "abc@test.com";

        Mockito.when(User.findByEmail(email)).thenReturn(null);
        Assertions.assertThrows(ResourceNotFound.class, () -> userService.deactivateUser(email));

    }

    @Test
    void test__deactivateUser_success() {
        PanacheMock.mock(User.class);
        String email = "abc@test.com";

        User user = Mockito.mock(User.class);
        PanacheMock.doReturn(user).when(User.class).findByEmail(email);
        Mockito.doNothing().when(user).persist();

        userService.deactivateUser(email);
        PanacheMock.verify(User.class, Mockito.times(1)).findByEmail(email);
        Mockito.verify(user, Mockito.times(1)).persist();
    }

    @Test
    void test__activateUser_userNotExists_throwsException() {
        PanacheMock.mock(User.class);
        String email = "abc@test.com";

        Mockito.when(User.findByEmail(email)).thenReturn(null);
        Assertions.assertThrows(ResourceNotFound.class, () -> userService.activateUser(email));

    }

    @Test
    void test__activateUser_success() {
        PanacheMock.mock(User.class);
        String email = "abc@test.com";

        User user = Mockito.mock(User.class);
        PanacheMock.doReturn(user).when(User.class).findByEmail(email);
        Mockito.doNothing().when(user).persist();
        userService.activateUser(email);
        PanacheMock.verify(User.class, Mockito.times(1)).findByEmail(email);
        Mockito.verify(user, Mockito.times(1)).persist();
    }
}
