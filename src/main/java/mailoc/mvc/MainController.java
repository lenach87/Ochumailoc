package mailoc.mvc;

import mailoc.data.Message;
import mailoc.data.MessageRepository;
import mailoc.data.User;
import mailoc.data.UserRepository;
import mailoc.security.CurrentUser;
import mailoc.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.NamingException;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/")
public class MainController {

	private final MessageRepository messageRepository;
	private final UserRepository userRepository;

	@Autowired
	public MainController(MessageRepository messageRepository, UserRepository userRepository) {
		this.messageRepository = messageRepository;
		this.userRepository = userRepository;
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		return model;

	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView defaultPage(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("index");
		return model;

	}

	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public ModelAndView success() {
		return new ModelAndView("success");
	}

	@RequestMapping(value = "/contacts")
	public ModelAndView contacts() {
		return new ModelAndView("contacts");
	}

	@RequestMapping(value = "/incoming", method = RequestMethod.GET)
	public ModelAndView listIncoming(@CurrentUser User currentUser) {
		Iterable<Message> incoming = messageRepository.findByReceiverId(currentUser.getId());
		ArrayList<Message> messages = new ArrayList<Message>();
		for (Message element : incoming) {
			if (!element.isIsRemovedByReceiver() && !element.isDeletedByReceiver()) {
				messages.add(element);
			}
		}
		return new ModelAndView("messages/incoming", "messages", messages);
	}

	@RequestMapping(value = "/outgoing", method = RequestMethod.GET)
	public ModelAndView listOutgoing(@CurrentUser User currentUser) {
		Iterable<Message> outgoing = messageRepository.findBySenderId(currentUser.getId());
		ArrayList<Message> messages = new ArrayList<Message>();
		for (Message element : outgoing) {
			if ((!element.isIsRemovedBySender()) && (!element.isDeletedBySender())) {
				messages.add(element);
			}
		}
		return new ModelAndView("messages/outgoing", "messages", messages);
	}

	@RequestMapping(value = "/deleted", method = RequestMethod.GET)
	public ModelAndView listDeleted(@CurrentUser User currentUser) {
		Iterable<Message> allMessages = messageRepository.findByReceiverIdOrSenderId(currentUser.getId(), currentUser.getId());
		ArrayList<Message> deletedMessages = new ArrayList<Message>();
		for (Message element : allMessages) {
			if ((element.isIsRemovedBySender()) && (Objects.equals(element.getSender().getId(), currentUser.getId()))) {
				deletedMessages.add(element);
			}
			if ((element.isIsRemovedByReceiver()) && (Objects.equals(element.getReceiver().getId(), currentUser.getId()))) {
				deletedMessages.add(element);
			}
		}
		return new ModelAndView("messages/deleted", "deletedMessages", deletedMessages);
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(value = "id") Long id) {
		Message message = messageRepository.findOne(id);
		return new ModelAndView("messages/view", "message", message);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@Transactional
	public ModelAndView deleteMessage(@RequestParam(value = "id") Long id, RedirectAttributes redirect,
									  @CurrentUser SecurityUser currentUser) {

		Message message = messageRepository.findById(id);


		if ((!message.isIsRemovedByReceiver()) && (Objects.equals(message.getReceiver().getId(), currentUser.getId()))) {
			message.setIsRemovedByReceiver(true);
			messageRepository.saveAndFlush(message);
			redirect.addFlashAttribute("globalMessage", "Message removed successfully");
			return new ModelAndView("redirect:/incoming");
		}

		else if ((message.isIsRemovedByReceiver()) && (Objects.equals(message.getReceiver().getId(), currentUser.getId()))) {
			message.setIsRemovedByReceiver(false);
			message.setDeletedByReceiver(true);
			message = messageRepository.saveAndFlush(message);
			if ((message.isDeletedBySender()) && (message.isDeletedByReceiver())) {
				messageRepository.delete(message);
			}
			redirect.addFlashAttribute("globalMessage", "Message removed successfully");
			return new ModelAndView("redirect:/deleted");
		}

		if ((!message.isIsRemovedBySender()) && (Objects.equals(message.getSender().getId(), currentUser.getId()))) {
			message.setIsRemovedBySender(true);
			messageRepository.saveAndFlush(message);
			redirect.addFlashAttribute("globalMessage", "Message removed successfully");
			return new ModelAndView("redirect:/outgoing");
		}

		else //((message.isIsRemovedBySender()) && (Objects.equals(message.getSender().getId(), currentUser.getId())))
			{
			message.setIsRemovedBySender(false);
			message.setDeletedBySender(true);
			message = messageRepository.saveAndFlush(message);
			if ((message.isDeletedBySender()) && (message.isDeletedByReceiver())) {
				messageRepository.delete(message);
			}
			redirect.addFlashAttribute("globalMessage", "Message removed successfully");
			return new ModelAndView("redirect:/deleted");
		}
	}

	@RequestMapping(value = "/compose", method = RequestMethod.GET)
	public String composeForm(@ModelAttribute MessageForm messageForm) {
		return "messages/compose";
	}

	@RequestMapping(value = "/compose", method = RequestMethod.POST)
	@Transactional
	public ModelAndView compose(@CurrentUser User currentUser, @Valid MessageForm messageForm, BindingResult result,
								RedirectAttributes redirect) {
		User to = userRepository.findUserByUsername(messageForm.getReceiverUsername());
		if (to == null) {
			result.rejectValue("receiverId", "receiverId", "User not found");
		}
		if (result.hasErrors()) {
			return new ModelAndView("messages/compose");
		}

		Message message = new Message();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date date = new Date();
		String messageDate = dateFormat.format(date);
		message.setSummary(messageForm.getSummary());
		message.setMessageText(messageForm.getMessageText());
		message.setDate(messageDate);
		message.setReceiver(to);
		message.setSender(currentUser);
		message.setIsRemovedBySender(false);
		message.setIsRemovedByReceiver(false);
		message.setDeletedByReceiver(false);
		message.setDeletedBySender(false);
		message = messageRepository.save(message);

		redirect.addFlashAttribute("globalMessage", "Message added successfully");
		return new ModelAndView("messages/view", "message", message);
	}

	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public String replyForm(@RequestParam(value = "id") Long id, @ModelAttribute MessageForm messageForm) {
		Message message = messageRepository.findById(id);
		messageForm.setReceiverUsername(message.getSender().getUsername());
		messageForm.setSummary("RE: " + message.getSummary());
		return "messages/compose";
	}
}
/*/
	@RequestMapping(value = "/reply", method=RequestMethod.POST)
	@Transactional
	public ModelAndView reply(@CurrentUser User currentUser, @Valid MessageForm messageForm, BindingResult result,
							  RedirectAttributes redirect) {
		User to = userRepository.findUserByUsername(messageForm.getReceiverUsername());
		if(to == null) {
			result.rejectValue("receiverUsername","receiverUsername", "User not found");
		}
		if(result.hasErrors()) {
			return new ModelAndView("messages/compose");
		}

		Message message = new Message();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date date = new Date();
		String messageDate = dateFormat.format(date);
		message.setSummary(messageForm.getSummary());
		message.setMessageText(messageForm.getMessageText());
		message.setDate(messageDate);
		message.setReceiver(to);
		message.setSender(currentUser);
		message.setIsRemovedBySender(false);
		message.setIsRemovedByReceiver(false);
		message.setDeletedByReceiver(false);
		message.setDeletedBySender(false);
		message = messageRepository.save(message);
		if (to != null) {
			to.getIncoming().add(message);
		}
		currentUser.getOutgoing().add(message);
		redirect.addFlashAttribute("globalMessage", "Message added successfully");
		return new ModelAndView("redirect:/{message.id}", "message.id", message.getId());
	}
*/



