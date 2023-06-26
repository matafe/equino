package com.matafe.equino.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.matafe.equino.model.Animal;
import com.matafe.equino.model.Odontogram;
import com.matafe.equino.model.Owner;
import com.matafe.equino.repository.AnimalRepository;
import com.matafe.equino.repository.OdontogramRepository;
import com.matafe.equino.repository.OwnerRepository;

import jakarta.servlet.http.HttpServletRequest;
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
	public String odontogramForm(Odontogram odontogram, Model model, HttpServletRequest request) {
		List<Owner> owners = this.ownerRepository.findAll();

		// pre-select
		String animalId = (String) request.getParameter("animalId");
		if (animalId != null && !animalId.trim().isEmpty()) {
			Optional<Animal> found = this.animalRepository.findById(Long.valueOf(animalId));
			found.ifPresent(odontogram::setAnimal);
			Animal animalFound = found.get();
			model.addAttribute("animal", animalFound);
			model.addAttribute("owner", ownerRepository.findById(animalFound.getOwnerId()).get());
		}
		
		model.addAttribute("activePage", "odontogram");
		model.addAttribute("owners", owners);
		
		odontogram.setCheckUpDate(LocalDate.now());

		return "odontogram";
	}

	@PostMapping("/odontogram")
	public String saveOdontogram(@Valid Odontogram odontogram, BindingResult bindingResult, Model model,
			RedirectAttributes attributes, Locale locale, HttpServletRequest request) {

		if (bindingResult.hasErrors()) {
			return odontogramForm(odontogram, model, request);
		}

		odontogramRepository.save(odontogram);

//		attributes.addFlashAttribute("message",
//				messageSource.getMessage("odontogram.saved", new String[] { odontogram.getName() }, locale));

		return "redirect:/odontograms";
	}

}