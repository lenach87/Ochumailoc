package mailoc.service;


import mailoc.data.Message;
import mailoc.data.MessageRepository;

import mailoc.data.User;
import mailoc.data.UserRepository;
import mailoc.security.CurrentUser;
import mailoc.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MessageService  {


    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @PostAuthorize("hasPermission(returnObject, 'read')")
    public Message findOne(Long id) {
        return messageRepository.findOne(id);
    }

    @Transactional
    public ArrayList<Message> listIncoming(User currentUser) {
        Iterable<Message> incoming = messageRepository.findByReceiver(currentUser);
        ArrayList<Message> messages = new ArrayList<Message>();
        for (Message element : incoming) {
            if (!element.isIsRemovedByReceiver() && !element.isDeletedByReceiver()) {
                messages.add(element);
            }
        }
        Collections.sort(messages, Collections.reverseOrder());
        return messages;
    }

    @Transactional
    public ArrayList<Message> listOutgoing(User currentUser) {
        Iterable <Message> outgoing = messageRepository.findBySender(currentUser);
        ArrayList<Message> messages = new ArrayList<Message>();
        for (Message element : outgoing) {
            if (!element.isIsRemovedBySender() && !element.isDeletedBySender()) {
                messages.add(element);
            }
        }
        Collections.sort(messages, Collections.reverseOrder());
        return messages;
    }

    @Transactional
    public ArrayList<Message> listDeleted(User currentUser) {
        Iterable<Message> allMessages = messageRepository.findByReceiverOrSender(currentUser, currentUser);
        ArrayList<Message> deletedMessages = new ArrayList<Message>();
        for (Message element : allMessages) {
            if ((element.isIsRemovedBySender()) && (Objects.equals(element.getSender().getId(), currentUser.getId()))) {
                deletedMessages.add(element);
            }
            if ((element.isIsRemovedByReceiver()) && (Objects.equals(element.getReceiver().getId(), currentUser.getId()))) {
                deletedMessages.add(element);
            }
        }
        Collections.sort(deletedMessages, Collections.reverseOrder());
        return deletedMessages;
    }

    @Transactional
    public ArrayList<Message> searchByPattern (String pattern, User currentUser) {
        ArrayList<Message> allByPattern = messageRepository.findByPattern(pattern);
        ArrayList<Message> messages = new ArrayList<Message>();
        for (Message element : allByPattern) {
            if ((Objects.equals(element.getReceiver().getId(), currentUser.getId()))||(Objects.equals(element.getSender().getId(), currentUser.getId()))) {
                messages.add(element);
            }
        }
        Collections.sort(messages, Collections.reverseOrder());
        return messages;
    }

    @Transactional
    public void deleteIncoming (Long [] ids, User currentUser) {

        List<Message> selectedList = new ArrayList<Message> ();
        for (Long id:ids) {
            selectedList.add(messageRepository.findById(id));
        }
            for(Message message : selectedList) {
                if ((!message.isIsRemovedByReceiver()) && (Objects.equals(message.getReceiver().getId(), currentUser.getId()))
                        && (Objects.equals(message.getReceiver().getId(), message.getSender().getId()))) {
                    message.setIsRemovedByReceiver(true);
                    message.setIsRemovedBySender(true);
                    messageRepository.saveAndFlush(message);
                }

                else if ((!message.isIsRemovedByReceiver()) && (Objects.equals(message.getReceiver().getId(), currentUser.getId()))) {
                    message.setIsRemovedByReceiver(true);
                    messageRepository.saveAndFlush(message);

                }
            }
    }

    @Transactional
    public void deleteOutgoing (Long[] ids, SecurityUser currentUser) {

        List<Message> selectedList = new ArrayList<Message> ();
        for (Long id:ids) {
            selectedList.add(messageRepository.findById(id));
        }

            for(Message message : selectedList) {
                if ((!message.isIsRemovedBySender()) && (Objects.equals(message.getSender().getId(), currentUser.getId()))
                        && (Objects.equals(message.getReceiver().getId(), message.getSender().getId()))) {
                    message.setIsRemovedByReceiver(true);
                    message.setIsRemovedBySender(true);
                    messageRepository.saveAndFlush(message);
                }

                else if ((!message.isIsRemovedBySender()) && (Objects.equals(message.getSender().getId(), currentUser.getId()))) {
                    message.setIsRemovedBySender(true);
                    messageRepository.saveAndFlush(message);
                }
            }
        }


    @Transactional
    public void deleteRemoved(Long[] ids, User currentUser) {
        List<Message> selectedList = new ArrayList<Message> ();
        for (Long id:ids) {
            selectedList.add(messageRepository.findById(id));
        }
            for(Message message : selectedList) {
                 if ((message.isIsRemovedByReceiver()) && (Objects.equals(message.getReceiver().getId(), currentUser.getId()))
                        && (Objects.equals(message.getReceiver().getId(), message.getSender().getId()))) {
                    messageRepository.delete(message);
                 }

                 else if ((message.isIsRemovedByReceiver()) && (Objects.equals(message.getReceiver().getId(), currentUser.getId()))) {
                    message.setIsRemovedByReceiver(false);
                    message.setDeletedByReceiver(true);
                    message = messageRepository.saveAndFlush(message);
                    if ((message.isDeletedBySender()) && (message.isDeletedByReceiver())) {
                        messageRepository.delete(message);
                    }
                }

                else if ((message.isIsRemovedBySender()) && (Objects.equals(message.getSender().getId(), currentUser.getId())))
                {
                    message.setIsRemovedBySender(false);
                    message.setDeletedBySender(true);
                    message = messageRepository.saveAndFlush(message);
                    if ((message.isDeletedBySender()) && (message.isDeletedByReceiver())) {
                        messageRepository.delete(message);
                    }
                }
            }
        }

    @Transactional
    public Message compose (User currentUser, Message messageForm) {

        User to = userRepository.findUserByUsername(messageForm.getReceiverName());
        if (to == null) {
            return null;
        }

        else {

            Message message = new Message();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            String messageDate = dateFormat.format(date);
            message.setSummary(messageForm.getSummary());
            message.setMessageText(messageForm.getMessageText());
            message.setDate(messageDate);
            message.setReceiver(to);
            message.setSenderName(currentUser.getUsername());
            message.setReceiverName(to.getUsername());
            message.setSender(currentUser);
            message.setIsRemovedBySender(false);
            message.setIsRemovedByReceiver(false);
            message.setDeletedByReceiver(false);
            message.setDeletedBySender(false);
        //    message = messageRepository.save(message);
            return message;

        }
    }

}

