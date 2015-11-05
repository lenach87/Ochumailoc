package mailoc.security;

import mailoc.data.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by elena on 10/15/15.
 */
public class SecurityUser extends User implements UserDetails {

    private static final long serialVersionUID = 5639683223516504866L;
    public SecurityUser(User user) {
            if (user != null) {
                this.setId(user.getId());
                this.setFirstName(user.getFirstName());
                this.setLastName(user.getLastName());
                this.setEmail(user.getEmail());
                this.setUsername(user.getUsername());
                this.setPassword(user.getPassword());
                this.setIncoming(user.getIncoming());
                this.setOutgoing(user.getOutgoing());
            }
        }
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return AuthorityUtils.createAuthorityList("ROLE_USER");
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }