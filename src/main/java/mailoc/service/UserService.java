package mailoc.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mailoc.data.User;
import mailoc.data.UserRepository;


@Service
@Transactional
public class UserService
{
  //  public UserService() {
  //  }

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
