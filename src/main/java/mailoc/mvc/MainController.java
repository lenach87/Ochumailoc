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
		Iterable<Message> messages = messageService.listIncoming(currentUser);
		return new ModelAndView("messages/incoming", "messages", messages);
	}

	@RequestMapping(value = "/outgoing", method = RequestMethod.GET)
	public ModelAndView listOutgoing(@CurrentUser User currentUser) {
		Iterable<Message> messages = messageService.listOutgoing(currentUser);
		return new ModelAndView("messages/outgoing", "messages", messages);
	}

	@RequestMapping(value = "/deleted", method = RequestMethod.GET)
	public ModelAndView listDeleted(@CurrentUser User currentUser) {
		Iterable<Message> deletedMessages = messageService.listDeleted(currentUser);
		return new ModelAndView("messages/deleted", "deletedMessages", deletedMessages);
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(value = "id") Long id) {
		Message message = messageRepository.findOne(id);
		return new ModelAndView("messages/view", "message", message);
	}

	@RequestMapping(value = "/search", method = {RequestMethod.GET, RequestMethod.POST} )
	public ModelAndView searchIncoming (@CurrentUser User currentUser, @RequestParam(value = "pattern") String pattern) {
		Iterable<Message> messages = messageService.searchByPattern(currentUser, pattern);
		return new ModelAndView("messages/incoming", "messages", messages);
	}

	@RequestMapping(value = "/delete_incoming", method = RequestMethod.POST)
	@Transactional
	public ModelAndView deleteIncoming(@RequestParam(value = "toDelete[]", required = false) Long[] toDelete,
									   RedirectAttributes redirect,
									   @CurrentUser SecurityUser currentUser) {

		if (toDelete != null) {
			messageService.deleteIncoming(toDelete, currentUser);
		}

		return new ModelAndView("redirect:/incoming");
	}

	@RequestMapping(value = "/delete_outgoing", method = RequestMethod.POST)
	@Transactional
	public ModelAndView deleteOutgoing(@RequestParam(value = "toDelete[]", required = false) Long[] toDelete,
									   RedirectAttributes redirect,
									   @CurrentUser SecurityUser currentUser) {
		if (toDelete != null) {
			messageService.deleteOutgoing(toDelete, currentUser);
		}
		return new ModelAndView("redirect:/outgoing");

	}

	@RequestMapping(value = "/delete_removed", method = RequestMethod.POST)
	@Transactional
	public ModelAndView deleteRemoved (@RequestParam(value = "toDelete[]", required = false) Long[] toDelete,
									   RedirectAttributes redirect,
									   @CurrentUser SecurityUser currentUser) {
		if (toDelete != null) {
			messageService.deleteRemoved(toDelete, currentUser);
		}
		return new ModelAndView("redirect:/deleted");
	}

	@RequestMapping(value = "/compose", method = RequestMethod.GET)
	public String composeForm(Model model) { //@ModelAttribute MessageForm messageForm) {
		Message messageForm = new Message();
		model.addAttribute("messageForm", messageForm);

		return "messages/compose";
	}

	@RequestMapping(value = "/compose", method = RequestMethod.POST)
	@Transactional
	public ModelAndView compose(@CurrentUser User currentUser, @ModelAttribute("messageForm") @Valid Message messageForm,
								BindingResult result,
								ModelMap model, RedirectAttributes redirect) {


			model.addAttribute("receiverName", messageForm.getReceiverName());
			model.addAttribute("summary", messageForm.getSummary());
			model.addAttribute("messageText", messageForm.getMessageText());

		if (messageService.compose(currentUser, messageForm)!=null) {
			Message message = messageService.compose(currentUser, messageForm);
			message=messageRepository.save(message);
			redirect.addFlashAttribute("globalMessage", "Message added successfully");
			return new ModelAndView("messages/view", "message", message);
		}
		else {
			result.rejectValue("receiverName", "receiverName", "User not found. If you do not know anyone, try sending to me, lenachu");
			return new ModelAndView("messages/compose");
		}
	}

	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public String replyForm(@RequestParam(value = "id") Long id, @ModelAttribute("messageForm") @Valid Message messageForm) {
		Message message = messageRepository.findById(id);
		messageForm.setReceiverName(message.getSender().getUsername());
		messageForm.setSummary("RE: " + message.getSummary());
		return "messages/compose";
	}
}




