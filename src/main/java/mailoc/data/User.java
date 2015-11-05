package mailoc.data;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "USER_ID")
    private Long id;

    @Column
    @Pattern (regexp = "^[a-zA-Z_-]{2,30}$", message = "First name shall consist of 2-30 English letters")
    @NotEmpty (message = "First name is required.")
    private String firstName;

    @Column
    @Pattern (regexp = "^[a-zA-Z_-]{2,30}$", message = "Last name shall consist of 2-30 English letters")
    @NotEmpty (message = "Last name is required.")
    private String lastName;

    @Column(unique = true)
    @Pattern (regexp = "^([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})$", message = "Please enter a valid email.")
    @NotEmpty (message = "Email is required.")
    private String email;

    @Column(unique = true)
    @Pattern (regexp = "^[a-z0-9_-]{6,30}$", message = "Username shall consist of 6-30 English letters, numbers and may contain _ or - signs")
    @NotEmpty (message = "Login is required.")
    private String username;

    @Column
    @Size(min=6, max=30, message="The password must be between 6 and 30 characters")
    @NotEmpty(message = "Password is required.")
    private String password;

    @Column
    @OneToMany// (cascade = CascadeType.ALL)
    private Set <Message> incoming = new HashSet<Message>();

    @Column
    @OneToMany// (cascade = CascadeType.ALL)
    private Set <Message> outgoing = new HashSet<Message>();

    public User() {}

    public User(Long id, String firstName, String lastName, String email, String username, String password, Set <Message> incoming, Set <Message> outgoing) {

        this.id =id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.incoming = incoming;
        this.outgoing = outgoing;
    }

    public User(User user) {
        this.id = user.id;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.email = user.email;
        this.username = user.username;
        this.password = user.password;
        this.incoming = user.incoming;
        this.outgoing = user.outgoing;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Message> getIncoming() {
        return incoming;
    }

    public void setIncoming(Set<Message> incoming) {
        this.incoming = incoming;
    }

    public Set<Message> getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(Set<Message> outgoing) {
        this.outgoing = outgoing;
    }
}