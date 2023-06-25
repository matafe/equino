package com.matafe.equino.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@Deprecated
public class NavigationController {

	@GetMapping("/info")
	public String info(Model model, HttpServletRequest request) {
		model.addAttribute("activePage", "info");
		return request.getRequestURI();
	}

	@GetMapping("/contact")
	public String contact(Model model, HttpServletRequest request) {
		model.addAttribute("activePage", "contact");
		return request.getRequestURI();
	}

}
