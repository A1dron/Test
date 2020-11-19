package Toster.services;

import Toster.entity.User;

public interface UserService {


    boolean authorization(User user);

    User registration(User user);

    void makeAdmin(User user);
}
