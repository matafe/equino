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

import com.matafe.equino.model.Owner;
import com.matafe.equino.repository.AnimalRepository;
import com.matafe.equino.repository.OwnerRepository;

import jakarta.validation.Valid;

@Controller
public class OwnerController implements MessageSourceAware {

	private MessageSource messageSource;

	@Autowired
	private OwnerRepository ownerRepository;

	@Autowired
	private AnimalRepository animalRepository;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping("/owners")
	public String owners(Model model) {
		List<Owner> owners = this.ownerRepository.findAll();
		model.addAttribute("owners", owners);
		return "owners";
	}

	@GetMapping("/owner")
	public String ownerForm(Owner owner) {
		return "owner";
	}

	@PostMapping("/owner")
	public String saveOwner(@Valid Owner owner, BindingResult bindingResult, Model model, RedirectAttributes attributes,
			Locale locale) {

		if (bindingResult.hasErrors()) {
			return "owner";
		}

		List<Owner> founds = ownerRepository.findByCpf(owner.getCpf());
		if (!founds.isEmpty()) {
			bindingResult.rejectValue("cpf", "error.cpf.exists");
			return "owner";
		}
		founds = ownerRepository.findByEmailIgnoreCase(owner.getEmail());
		if (!founds.isEmpty()) {
			bindingResult.rejectValue("email", "error.email.exists");
			return "owner";
		}

		ownerRepository.save(owner);

		attributes.addFlashAttribute("message",
				messageSource.getMessage("owner.saved", new String[] { owner.getName() }, locale));
		attributes.addFlashAttribute("owner", owner);

		return "redirect:/owners";
	}

}
