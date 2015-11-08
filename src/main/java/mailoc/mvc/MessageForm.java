/* package mailoc.mvc;


import mailoc.data.User;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class MessageForm {

    @NotEmpty(message = "Username is required.")
    private String receiverName;

    @NotEmpty(message = "Summary is required.")
    private String summary;

    @Size(min=1, max=1000, message="The message must be between 1 than 1000 characters")
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

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName (String receiverName) {
        this.receiverName = receiverName;
    }
}
*/