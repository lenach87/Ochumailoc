package mailoc.service;


import mailoc.data.Message;
import mailoc.data.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Service
@Transactional
public class MessageService  {
  //  public MessageService() {
  //  }

    @Autowired
    MessageRepository messageRepository;
    Message findOne(Long id) {
        return messageRepository.findOne(id);
    }


}

