package mailoc.security;

import mailoc.data.User;
import mailoc.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.List;

import mailoc.service.UserService;
import org.springframework.transaction.annotation.Transactional;


@Service ("customUserDetailsService")
public class UserRepositoryUserDetailsService implements UserDetailsService {


    @Autowired
    private UserService userService;

    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userService.findUserByUsername(username);
        if(user==null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("Username not found");
        }
        return new SecurityUser(user);
    }
}
