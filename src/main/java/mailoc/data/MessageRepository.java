package mailoc.data;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;

//@PersistenceContext(unitName = "MailJPA")
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Iterable<Message> findByReceiverId(Long id);

    Message findOne(Long id);

    Message findById(Long id);

    Iterable<Message> findBySenderId(Long id);

    void delete (Long id);

    Iterable<Message> findByReceiverIdOrSenderId(Long id, Long id2);

   // void deleteAllIsRemovedEqualsTrue ();
}