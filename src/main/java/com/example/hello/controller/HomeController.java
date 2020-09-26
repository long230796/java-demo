package com.example.hello.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.hello.model.Person;

@Controller  //  chỉ cho spring biết đây là controller 
public class HomeController {
	
	private static List<Person> persons = new ArrayList<Person>();  // static list được load lên ở thời điểm tạo ứng dụng.
	static {
		persons.add(new Person("Long", "Cao"));  // add vào list persons sử dụng constructor 
		persons.add(new Person("J19", "Room"));
	}
	
	@RequestMapping(value = {"/personList"}, method = RequestMethod.GET)
	public String personList(Model model) {
		model.addAttribute("persons", persons);  // add attribute persons va them list persons vao 
		return "personList"; // ten cua file html 
	}
	
	
	@RequestMapping(value = {"/addPerson"}, method = RequestMethod.GET)    // render page html và đưa đối tượng rỗng lên front-end.
	public String showAddPersonPage(Model model) {
		Person personForm = new Person();  // khi get addPerson thi no se tao 1 personForm rong theo Person()
		model.addAttribute("personForm", personForm); // gui personform len lai font-end 
		return "addPerson";
	}
	
	@RequestMapping(value = {"/addPerson"}, method = RequestMethod.POST)   //// gửi dữ liệu từ front-end về để xử lí 
	public String savePerson (Model model, @ModelAttribute("personForm") Person personForm) {
		String firstName = personForm.getFirstName();
		String lastName = personForm.getLastName();
		
		if (firstName != null && firstName.length() != 0 && lastName != null && lastName.length() != 0) {
			persons.add(personForm);
			
			return "redirect:/personList";
		}
		// xử lí lỗi
		model.addAttribute("errorMessage", "first name & last name are required");
		return "addPerson";
	}
	
	@RequestMapping("/")
	public String welcome(final Model model) {
		model.addAttribute("message", "hello");  // sử dụng Model của spring để đưa giá trị từ back lên front (đưa mesage lên)
		return "index";
	}
	
	@RequestMapping("/hello/{param}")
	@ResponseBody // báo cho spring boot là load đoạn text ra mà không lấy file html. 
	public String hello(@PathVariable("param") String param) {
		return "hello" + param;
	}
}
