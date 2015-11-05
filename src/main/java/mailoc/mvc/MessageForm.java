package mailoc.mvc;


import mailoc.data.User;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class MessageForm {

    @NotEmpty(message = "Username is required.")
    private String receiverUsername;

    @NotEmpty(message = "Summary is required.")
    private String summary;

  //  @NotEmpty(message = "Message is required.")
    private String messageText;


    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername (String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }
}
