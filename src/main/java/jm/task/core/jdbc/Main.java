package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("John", "Cohen", (byte) 74);
        userService.saveUser("David", "Johnson", (byte) 47);
        userService.saveUser("Mark", "Davidson", (byte) 22);
        userService.saveUser("Noah", "Davidson", (byte) 18);

        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}