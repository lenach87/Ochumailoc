package mailoc.data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Messages")
public class Message implements Serializable, Comparable<Message> {
    @Id
    @GeneratedValue
    @Column (name = "Message_ID")
    private Long id;

    @Column (nullable = false)
    @Size(min=1, max=50, message="The summary must be between 1 than 50 characters")
    private String summary;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name="Message_Incoming",
            joinColumns={@JoinColumn(name="Message_ID")},
            inverseJoinColumns={@JoinColumn(name="USER_ID")})
    private User receiver;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name="Message_Outgoing",
            joinColumns={@JoinColumn(name="Message_ID")},
            inverseJoinColumns={@JoinColumn(name="USER_ID")})
    private User sender;

    private String receiverName;

    private String senderName;

    @Column
    private String date;

    @Column (nullable = false)
    @Size(min=1, max=1000, message="The message must be between 1 than 1000 characters")
    private String messageText;

    @Column(name = "RemovedBySender")
    private boolean isRemovedBySender;

    @Column(name = "RemovedByReceiver")
    private boolean isRemovedByReceiver;

    @Column(name = "DeletedBySender")
    private boolean deletedBySender;

    @Column(name = "DeletedByReceiver")
    private boolean deletedByReceiver;

    public Message() {
    }

    public Message(Long id, String summary, User receiver, User sender, String date,
                   String messageText, String receiverName, String senderName, boolean isRemovedBySender,
                   boolean isRemovedByReceiver, boolean deletedByReceiver, boolean deletedBySender) {

        this.id=id;
        this.summary = summary;
        this.receiver = receiver;
        this.receiverName = receiver.getUsername();
        this.sender = sender;
        this.senderName = sender.getUsername();
        this.date = date;
        this.messageText = messageText;
        this.isRemovedBySender = isRemovedBySender;
        this.isRemovedByReceiver = isRemovedByReceiver;
        this.deletedByReceiver=deletedByReceiver;
        this.deletedBySender=deletedBySender;
    }

    public Message(Long id, String summary, User receiver, User sender, String date, String messageText) {

        this.id=id;
        this.summary = summary;
        this.receiver = receiver;
        this.receiverName = receiver.getUsername();
        this.sender = sender;
        this.senderName = sender.getUsername();
        this.date = date;
        this.messageText = messageText;
        this.isRemovedBySender = false;
        this.isRemovedByReceiver = false;
        this.deletedByReceiver=false;
        this.deletedBySender=false;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getSummary() {return summary;}
    public void setSummary(String summary) {this.summary = summary;}

    public User getReceiver() {return receiver;}
    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }
    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getMessageText() {return messageText;}
    public void setMessageText(String messageText) {this.messageText = messageText;}

    public boolean isIsRemovedBySender() {
        return isRemovedBySender;
    }
    public void setIsRemovedBySender(boolean isRemovedBySender) {
        this.isRemovedBySender = isRemovedBySender;
    }

    public boolean isIsRemovedByReceiver() {return isRemovedByReceiver;}

    public void setIsRemovedByReceiver(boolean isRemovedByReceiver) {this.isRemovedByReceiver = isRemovedByReceiver;}

    public boolean isDeletedBySender() {
        return deletedBySender;
    }

    public void setDeletedBySender(boolean deletedBySender) {
        this.deletedBySender = deletedBySender;
    }

    public boolean isDeletedByReceiver() {
        return deletedByReceiver;
    }

    public void setDeletedByReceiver(boolean deletedByReceiver) {
        this.deletedByReceiver = deletedByReceiver;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public int compareTo(Message p)
    {
        return getId().compareTo(p.getId());
    }

    static class MessageComparator implements Comparator<Message>
    {
        public int compare(Message p1, Message p2)
        {
            Long id1 = p1.getId();
            Long id2 = p2.getId();

            if (id1 == id2)
                return 0;
            else if (id1 > id2)
                return 1;
            else
                return -1;
        }
    }
}


