package academy.devdojo.springboot2.util.userModel;

import academy.devdojo.springboot2.requests.userModelRequests.UserModelPostRequestBody;

public class UserModelPostRequestBodyCreator {

    public static UserModelPostRequestBody createUserModelPostRequestBodyUser() {
        String name = UserModelCreator.createValidUserModelUser().getName();
        String username = UserModelCreator.createValidUserModelUser().getUsername();
        String password = UserModelCreator.createValidUserModelUser().getPassword();
        String authorities = UserModelCreator.createValidUserModelUser().getAuthorities().toString();
        return new UserModelPostRequestBody(name, username, password, authorities, null);
    }

    public static UserModelPostRequestBody createUserModelPostRequestBodyAdmin() {
        String name = UserModelCreator.createValidUserModelAdmin().getName();
        String username = UserModelCreator.createValidUserModelAdmin().getUsername();
        String password = UserModelCreator.createValidUserModelAdmin().getPassword();
        String authorities = UserModelCreator.createValidUserModelAdmin().getAuthorities().toString();
        return new UserModelPostRequestBody(name, username, password, authorities, null);
    }
}
