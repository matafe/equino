package com.matafe.equino.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.matafe.equino.model.Odontogram;
import com.matafe.equino.model.Owner;
import com.matafe.equino.repository.AnimalRepository;
import com.matafe.equino.repository.OdontogramRepository;
import com.matafe.equino.repository.OwnerRepository;

import jakarta.validation.Valid;

@Controller
public class OdontogramController implements MessageSourceAware {

	private MessageSource messageSource;

	@Autowired
	private OdontogramRepository odontogramRepository;

	@Autowired
	private AnimalRepository animalRepository;

	@Autowired
	private OwnerRepository ownerRepository;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping("/odontogram")
	public String odontogramForm(Odontogram odontogram, Model model) {
		List<Owner> owners = this.ownerRepository.findAll();

		model.addAttribute("activePage", "odontogram");
		model.addAttribute("owners", owners);

		return "odontogram";
	}

	@PostMapping("/odontogram")
	public String saveOdontogram(@Valid Odontogram odontogram, BindingResult bindingResult, Model model,
			RedirectAttributes attributes, Locale locale) {

		if (bindingResult.hasErrors()) {
			return odontogramForm(odontogram, model);
		}

		odontogramRepository.save(odontogram);

//		attributes.addFlashAttribute("message",
//				messageSource.getMessage("odontogram.saved", new String[] { odontogram.getName() }, locale));

		return "redirect:/odontograms";
	}

}