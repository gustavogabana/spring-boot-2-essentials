package academy.devdojo.springboot2.util.userModel;

import academy.devdojo.springboot2.domain.UserModel;

public class UserModelCreator {
    public static UserModel createUserModelToBeSavedUser() {
        return new UserModel(null,
                "Teste",
                "test-user",
                "{bcrypt}$2a$10$cKO0WbdeynWpOoYaqsIqYeELGbNAH36H7xBJ9kN2yByKCN1fgAkQy",
                "ROLE_USER");
    }

    public static UserModel createUserModelToBeSavedAdmin() {
        return new UserModel(null,
                "Teste",
                "test-admin",
                "{bcrypt}$2a$10$cKO0WbdeynWpOoYaqsIqYeELGbNAH36H7xBJ9kN2yByKCN1fgAkQy",
                "ROLE_ADMIN");
    }

    public static UserModel createValidUserModelUser() {
        return new UserModel(1L,
                "Teste",
                "test-user",
                "{bcrypt}$2a$10$cKO0WbdeynWpOoYaqsIqYeELGbNAH36H7xBJ9kN2yByKCN1fgAkQy",
                "ROLE_USER");
    }

    public static UserModel createValidUserModelAdmin() {
        return new UserModel(1L,
                "Teste",
                "test-admin",
                "{bcrypt}$2a$10$cKO0WbdeynWpOoYaqsIqYeELGbNAH36H7xBJ9kN2yByKCN1fgAkQy",
                "ROLE_ADMIN");
    }

    public static UserModel createValidUpdatedUserModelUserToAdmin() {
        return new UserModel(1L,
                "Teste",
                "test-admin",
                "{bcrypt}$2a$10$cKO0WbdeynWpOoYaqsIqYeELGbNAH36H7xBJ9kN2yByKCN1fgAkQy",
                "ROLE_ADMIN");
    }

    public static UserModel createValidUpdatedUserModelAdminToUser() {
        return new UserModel(1L,
                "Teste",
                "test-admin",
                "{bcrypt}$2a$10$cKO0WbdeynWpOoYaqsIqYeELGbNAH36H7xBJ9kN2yByKCN1fgAkQy",
                "ROLE_USER");
    }
}
