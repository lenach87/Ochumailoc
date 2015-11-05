package mailoc.security;

import mailoc.data.Message;
import mailoc.data.User;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Component
public class MessagePermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Object targetDomainObject, Object permission) {
        if(authentication == null) {
            return false;
        }
        Message message = (Message) targetDomainObject;

        if(message == null) {
            return true;
        }

        User currentUser = (User) authentication.getPrincipal();
        if ((currentUser.getUsername().equals((message.getSender().getUsername()))) || (currentUser.getUsername().equals((message.getReceiver().getUsername()))))
        return true;
        else
            return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Serializable targetId, String targetType, Object permission) {
        throw new UnsupportedOperationException();
    }

}


