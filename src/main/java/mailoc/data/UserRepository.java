package mailoc.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;


//@PersistenceContext (unitName = "MailJPA")
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.username=?1 and u.password=?2")
    User login(String username, String password);

    User findByUsernameAndPassword(String username, String password);
    User findUserByUsername(String username);

}