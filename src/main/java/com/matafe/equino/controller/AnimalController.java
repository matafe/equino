package com.matafe.equino.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.matafe.equino.model.Animal;
import com.matafe.equino.model.Breed;
import com.matafe.equino.model.Gender;
import com.matafe.equino.model.Owner;
import com.matafe.equino.repository.AnimalRepository;
import com.matafe.equino.repository.BreedRepository;
import com.matafe.equino.repository.OwnerRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/animals")
public class AnimalController implements MessageSourceAware {

	private MessageSource messageSource;

	@Autowired
	private AnimalRepository animalRepository;
	
	@Autowired
	private BreedRepository breedRepository;

	@Autowired
	private OwnerRepository ownerRepository;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping
	public String animals(Model model) {
		List<Animal> animals = this.animalRepository.fetchAll();
		model.addAttribute("animals", animals);
		return "animals/animals";
	}

	@GetMapping("add")
	public String animalForm(Animal animal, Model model, HttpServletRequest request) {
		List<Breed> breeds = this.breedRepository.findAll();
		List<Owner> owners = this.ownerRepository.findAll();

		// pre-select
		String ownerId = (String) request.getParameter("ownerId");
		if (ownerId != null && !ownerId.trim().isEmpty()) {
			Optional<Owner> found = this.ownerRepository.findById(Long.valueOf(ownerId));
			found.ifPresent(animal::setOwner);
			model.addAttribute("owner", found.get());
		}

		model.addAttribute("activePage", "animal");
		model.addAttribute("genders", Gender.values());
		model.addAttribute("breeds", breeds);
		model.addAttribute("owners", owners);

		return "animals/animalForm";
	}

	@PostMapping("add")
	public String saveAnimal(@Valid Animal animal, BindingResult bindingResult, Model model,
			RedirectAttributes attributes, Locale locale, HttpServletRequest request) {

		if (bindingResult.hasErrors()) {
			return animalForm(animal, model, request);
		}

		animalRepository.save(animal);

		attributes.addFlashAttribute("message",
				messageSource.getMessage("animal.saved", new String[] { animal.getName() }, locale));
		attributes.addFlashAttribute("animal", animal);

		return "redirect:/animals";
	}

	@GetMapping("{id}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes attributes, Locale locale) {

		animalRepository.deleteById(id);
		attributes.addFlashAttribute("message", messageSource.getMessage("record.deleted", new String[0], locale));

		return "redirect:/animals";
	}

//	@GetMapping(value = "owners/{id}/animals", produces = "application/json")
//	public @ResponseBody List<AnimalDTO> getAnimalsFromOwner(@PathVariable Long id) {
//		Optional<Owner> ownerOptional = this.ownerRepository.findById(id);
//		if (ownerOptional.isPresent()) {
//			Owner owner = ownerOptional.get();
//			List<Animal> animals = this.animalRepository.findByOwner(owner);
//			return animals.stream().map(a -> new AnimalDTO(a.getId(), a.getName())).collect(toList());
//
//		}
//		return Collections.emptyList();
//
//	}

}
