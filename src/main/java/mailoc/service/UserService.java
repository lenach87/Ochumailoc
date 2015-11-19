package mailoc.service;



import java.util.ArrayList;
import java.util.List;

import mailoc.data.Message;
import mailoc.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mailoc.data.User;
import mailoc.data.UserRepository;


@Service
@Transactional
public class UserService
{

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
