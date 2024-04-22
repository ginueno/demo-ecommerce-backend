package ecommerce.finalproject.service.User;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.finalproject.config.JwtProvider;
import ecommerce.finalproject.entity.User;
import ecommerce.finalproject.exception.User.UserException;
import ecommerce.finalproject.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService2{

    @Autowired
    UserRepo userRepo;

    @Autowired
    JwtProvider jwtProvider;

    @Override
    public User findById(Long userId) throws UserException {
        Optional<User> user = userRepo.findById(userId);
        if(!user.isPresent()) {
            throw new UserException("User id không tồn tại");
        }
        return user.get();
    }

    @Override
    public User findProfileByJwt(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromToken(jwt);

        User user = userRepo.findByEmail(email);
        
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepo.findAllByOrderByCreateAtDesc();
    }
    
}
