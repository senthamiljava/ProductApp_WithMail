package com.revature.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.revature.dao.UserDAO;
import com.revature.model.User;
import com.revature.util.EmailUtil;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private EmailUtil emailObj;

	@GetMapping("/list")
	public String list(ModelMap modelMap) {

		System.out.println("UserController -> list");
		List<User> userList = userDAO.findAll();
		modelMap.addAttribute("USER_LIST", userList);

		return "user_list";
	}

	@GetMapping("/create")
	public String create() {
		return "add_user";
	}

	@PostMapping("/save")
	public String save(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("password") String password, ModelMap map) throws Exception {

		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		System.out.println(user);
		userDAO.save(user);

		// Send Registration Notification Mail
		String subject = "Your account has been created";
		String body = "Welcome to Revature ! You can login to your account !";
		emailObj.send(email, subject, body);

		return "redirect:/users/list";
	}

	@GetMapping("/edit")
	public String edit(@RequestParam("id") Integer id, ModelMap model) {

		User user = userDAO.findOne(id);
		model.addAttribute("USER_DETAIL", user);
		return "update_user";
	}

	@GetMapping("/activate")
	public String activateAccount(@RequestParam("id") Integer id) {

		userDAO.activateAccount(id);
		return "redirect:/users/list";
	}

	@GetMapping("/deactivate")
	public String deActivateAccount(@RequestParam("id") Integer id) {

		userDAO.deActivateAccount(id);
		return "redirect:/users/list";
	}

	@PostMapping("/update")
	public String update(@RequestParam("id") Integer id, @RequestParam("name") String name,
			@RequestParam("email") String email, @RequestParam("password") String password) {

		User user = userDAO.findOne(id);
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);

		userDAO.update(user);

		return "redirect:/users/list";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("id") Integer id) {
		userDAO.delete(id);
		return "redirect:/users/list";
	}
}
