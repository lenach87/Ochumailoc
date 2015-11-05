package mailoc.mvc;


import java.util.*;
import javax.validation.Valid;

import mailoc.data.Message;
import mailoc.data.User;
import mailoc.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.repository.*;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserRepository userRepository;

    @Autowired
    public SignupController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String signupForm(Model model) {

        User userForm = new User();
        model.addAttribute ("userForm", userForm);
        return "signup";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView doSignup(@ModelAttribute("userForm") @Valid User user, BindingResult result, ModelMap model, RedirectAttributes redirect) {

        if(result.hasErrors()) {
            return new ModelAndView("signup");
        }
        model.addAttribute("firstName",user.getFirstName());
        model.addAttribute("lastName",user.getLastName());
        model.addAttribute("email",user.getEmail());
        model.addAttribute("username",user.getUsername());
        model.addAttribute("password",user.getPassword());

        user = this.userRepository.saveAndFlush(user);

        List<GrantedAuthority> authorities =
                AuthorityUtils.createAuthorityList("ROLE_USER");
        Authentication auth =
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return new ModelAndView("redirect:success");
    }
}