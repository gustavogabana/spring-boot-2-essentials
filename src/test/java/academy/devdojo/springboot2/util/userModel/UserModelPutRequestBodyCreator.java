package academy.devdojo.springboot2.util.userModel;

import academy.devdojo.springboot2.requests.userModelRequests.UserModelPutRequestBody;

public class UserModelPutRequestBodyCreator {
    public static UserModelPutRequestBody userModelPutRequestBodyUser() {
        Long id = UserModelCreator.createValidUpdatedUserModelUserToAdmin().getId();
        String name = UserModelCreator.createValidUpdatedUserModelUserToAdmin().getName();
        String username = UserModelCreator.createValidUpdatedUserModelUserToAdmin().getUsername();
        String password = UserModelCreator.createValidUpdatedUserModelUserToAdmin().getPassword();
        String authorities = UserModelCreator.createValidUpdatedUserModelUserToAdmin().getAuthorities().toString();
        return new UserModelPutRequestBody(id, name, username, password, authorities);
    }

    public static UserModelPutRequestBody userModelPutRequestBodyAdmin() {
        Long id = UserModelCreator.createValidUpdatedUserModelAdminToUser().getId();
        String name = UserModelCreator.createValidUpdatedUserModelAdminToUser().getName();
        String username = UserModelCreator.createValidUpdatedUserModelAdminToUser().getUsername();
        String password = UserModelCreator.createValidUpdatedUserModelAdminToUser().getPassword();
        String authorities = UserModelCreator.createValidUpdatedUserModelAdminToUser().getAuthorities().toString();
        return new UserModelPutRequestBody(id, name, username, password, authorities);
    }
}
