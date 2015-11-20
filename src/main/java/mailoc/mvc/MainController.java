package mailoc.mvc;

import mailoc.data.*;
import mailoc.security.CurrentUser;
import mailoc.security.SecurityUser;
import mailoc.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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
	private static MessageService messageService;

	@Autowired
	public void setMessageService(MessageService messageService) {
		MainController.messageService = messageService;
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
		Iterable<Message> incoming = messageRepository.findByReceiver(currentUser);
		ArrayList<Message> messages = new ArrayList<Message>();
		for (Message element : incoming) {
			if (!element.isIsRemovedByReceiver() && !element.isDeletedByReceiver()) {
				messages.add(element);
			}
		}
		Collections.sort(messages, Collections.reverseOrder());
		return new ModelAndView("messages/incoming", "messages", messages);
	}

	@RequestMapping(value = "/outgoing", method = RequestMethod.GET)
	public ModelAndView listOutgoing(@CurrentUser User currentUser) {

		Iterable <Message> outgoing = messageRepository.findBySender(currentUser);
		ArrayList<Message> messages = new ArrayList<Message>();
		for (Message element : outgoing) {
			if (!element.isIsRemovedBySender() && !element.isDeletedBySender()) {
				messages.add(element);
			}
		}
		Collections.sort(messages, Collections.reverseOrder());
		return new ModelAndView("messages/outgoing", "messages", messages);
	}

	@RequestMapping(value = "/deleted", method = RequestMethod.GET)
	public ModelAndView listDeleted(@CurrentUser User currentUser) {
		Iterable<Message> allMessages = messageRepository.findByReceiverOrSender(currentUser, currentUser);
		ArrayList<Message> deletedMessages = new ArrayList<Message>();
		for (Message element : allMessages) {
			if ((element.isIsRemovedBySender()) && (Objects.equals(element.getSender().getId(), currentUser.getId()))) {
				deletedMessages.add(element);
			}
			if ((element.isIsRemovedByReceiver()) && (Objects.equals(element.getReceiver().getId(), currentUser.getId()))) {
				deletedMessages.add(element);
			}
		}
		Collections.sort(deletedMessages, Collections.reverseOrder());
		return new ModelAndView("messages/deleted", "deletedMessages", deletedMessages);
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(value = "id") Long id) {
		Message message = messageRepository.findOne(id);
		return new ModelAndView("messages/view", "message", message);
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchIncoming (@CurrentUser User currentUser, @RequestParam(value = "pattern") String pattern) {
		Iterable<Message> allByPattern = messageRepository.findByMessageTextOrSummaryContainingIgnoreCase(pattern, pattern);
		ArrayList<Message> messages = new ArrayList<Message>();
		for (Message element : allByPattern) {
				messages.add(element);
		}

		Collections.sort(messages, Collections.reverseOrder());
		return new ModelAndView("messages/incoming", "messages", messages);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@Transactional
	public ModelAndView deleteMessage(//@ModelAttribute("messageForm") MessageForm messageForm, Model model,
									  // @RequestParam(value = "messageIds") String[] messageIds,
									  HttpServletRequest request,
									  RedirectAttributes redirect,
									  @CurrentUser SecurityUser currentUser) {


		final String[] checkboxes = request.getParameterValues("messageIds");
		List<Long> ids = new ArrayList<>();
		if (null != checkboxes) {
			for (String s : checkboxes) {
				ids.add(Long.parseLong(s));
			}
		}

		List<Message> selectedList = new ArrayList<Message> ();
		for (Long id:ids) {
			selectedList.add(messageRepository.findById(id));
		}

		if (selectedList.isEmpty()) {
			return new ModelAndView("redirect:/deleted");
		}
		else {
			for(Message message : selectedList) {
				if ((!message.isIsRemovedByReceiver()) && (Objects.equals(message.getReceiver().getId(), currentUser.getId()))
						&& (Objects.equals(message.getReceiver().getId(), message.getSender().getId()))) {
					message.setIsRemovedByReceiver(true);
					message.setIsRemovedBySender(true);
					messageRepository.saveAndFlush(message);
					redirect.addFlashAttribute("globalMessage", "Message removed successfully");
					return new ModelAndView("redirect:/incoming");
				} else if ((message.isIsRemovedByReceiver()) && (Objects.equals(message.getReceiver().getId(), currentUser.getId()))
						&& (Objects.equals(message.getReceiver().getId(), message.getSender().getId()))) {

					messageRepository.delete(message);

					redirect.addFlashAttribute("globalMessage", "Message removed successfully");
					return new ModelAndView("redirect:/deleted");
				}

				if ((!message.isIsRemovedByReceiver()) && (Objects.equals(message.getReceiver().getId(), currentUser.getId()))) {
					message.setIsRemovedByReceiver(true);
					messageRepository.saveAndFlush(message);
					redirect.addFlashAttribute("globalMessage", "Message removed successfully");
					return new ModelAndView("redirect:/incoming");
				} else if ((message.isIsRemovedByReceiver()) && (Objects.equals(message.getReceiver().getId(), currentUser.getId()))) {
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
		}
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/compose", method = RequestMethod.GET)
	public String composeForm(Model model) { //@ModelAttribute MessageForm messageForm) {
		Message messageForm = new Message();
		model.addAttribute("messageForm", messageForm);

		return "messages/compose";
	}

	@RequestMapping(value = "/compose", method = RequestMethod.POST)
	@Transactional
	public ModelAndView compose(@CurrentUser User currentUser, @ModelAttribute("messageForm") @Valid Message messageForm, BindingResult result,
								ModelMap model, RedirectAttributes redirect) {


			model.addAttribute("receiverName",messageForm.getReceiverName());
			model.addAttribute("summary",messageForm.getSummary());
			model.addAttribute("messageText",messageForm.getMessageText());

		User to = userRepository.findUserByUsername(messageForm.getReceiverName());
		if (to == null) {
			result.rejectValue("receiverName", "receiverName", "User not found");
		}
		if (result.hasErrors()) {
			return new ModelAndView("messages/compose");
		}

		Message message = new Message();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String messageDate = dateFormat.format(date);
		message.setSummary(messageForm.getSummary());
		message.setMessageText(messageForm.getMessageText());
		message.setDate(messageDate);
		message.setReceiver(to);
		message.setSenderName(currentUser.getUsername());
		message.setReceiverName(to.getUsername());
		message.setSender(currentUser);
		message.setIsRemovedBySender(false);
		message.setIsRemovedByReceiver(false);
		message.setDeletedByReceiver(false);
		message.setDeletedBySender(false);
		message = messageRepository.save(message);
//
		redirect.addFlashAttribute("globalMessage", "Message added successfully");
		return new ModelAndView("messages/view", "message", message);
	}

	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public String replyForm(@RequestParam(value = "id") Long id, @ModelAttribute("messageForm") @Valid Message messageForm) {
		Message message = messageRepository.findById(id);
		messageForm.setReceiverName(message.getSender().getUsername());
		messageForm.setSummary("RE: " + message.getSummary());
		return "messages/compose";
	}
}




