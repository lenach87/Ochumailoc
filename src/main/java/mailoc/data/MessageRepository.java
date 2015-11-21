package mailoc.data;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import java.util.ArrayList;

//@PersistenceContext(unitName = "MailJPA")
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {


    Iterable<Message> findByReceiver(User id);
    @PostAuthorize("hasPermission(returnObject, 'read')")
    Message findOne(Long id);
    @PostAuthorize("hasPermission(returnObject, 'read')")
    Message findById(Long id);

    Iterable<Message> findBySender(User id);

    @PostAuthorize("hasPermission(returnObject, 'read')")
    void delete (Long id);

    Iterable<Message>findByReceiverOrSender(User id, User id2);

    Iterable<Message>findByReceiverOrSenderAndMessageTextOrSummaryContainingIgnoreCase(User u1, User u2, String id, String id2);


}