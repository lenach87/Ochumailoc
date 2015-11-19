package mailoc.service;


import mailoc.data.Message;
import mailoc.data.MessageRepository;

import mailoc.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Service
@Transactional
public class MessageService  {


    @Autowired
    MessageRepository messageRepository;

    @PostAuthorize("hasPermission(returnObject, 'read')")
    public Message findOne(Long id) {
        return messageRepository.findOne(id);
    }


    public Iterable<Message> listOutgoing (User currentUser) {
        Iterable<Message> outgoing = messageRepository.findBySender(currentUser);
        ArrayList<Message> messages = new ArrayList<Message>();
        for (Message element : outgoing) {
            if ((!element.isIsRemovedBySender()) && (!element.isDeletedBySender())) {
                messages.add(element);
            }
        }
        return messages;
    }

}

