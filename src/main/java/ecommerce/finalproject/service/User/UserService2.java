package ecommerce.finalproject.service.User;

import java.util.List;

import ecommerce.finalproject.entity.User;
import ecommerce.finalproject.exception.User.UserException;

public interface UserService2 {
    public User findById(Long userId) throws UserException;

    public User findProfileByJwt(String jwt) throws UserException;

    public List<User> findAllUsers();
}
