package mailoc.service;


import mailoc.data.Message;
import mailoc.data.MessageRepository;

import mailoc.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


  //  public MessageForm listOutgoing (User currentUser) {
  //      Iterable<Message> outgoing = messageRepository.findBySender(currentUser);
  //      ArrayList<Message> messages = new ArrayList<Message>();
  //      MessageForm messageForm = new MessageForm();
  //      for (Message message : outgoing) {
  //          if ((!message.isIsRemovedBySender()) && (!message.isDeletedBySender())) {
  //              messages.add(message);
  //          }
  //      }
  //     messageForm.setMessageList(messages);
  //      return messageForm;
  //  }

}

