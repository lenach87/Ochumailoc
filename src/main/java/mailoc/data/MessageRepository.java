package mailoc.data;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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

    ArrayList<Message> findByReceiverOrSender(User id, User id2);


    @Query("select f from Message f where LOWER (f.messageText) LIKE LOWER(CONCAT('%',:pattern, '%')) OR\n" +
            "LOWER(f.summary) LIKE LOWER(CONCAT('%',:pattern, '%'))")
    ArrayList <Message> findByPattern(@Param("pattern") String pattern);
}