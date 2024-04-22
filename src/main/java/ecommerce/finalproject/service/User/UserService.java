package ecommerce.finalproject.service.User;

import org.springframework.security.core.userdetails.UserDetails;

import ecommerce.finalproject.exception.User.UserException;

public interface UserService {
    public UserDetails findByEmail(String email) throws UserException;

    public UserDetails findUserProfileByJwt(String jwt) throws UserException;
}
