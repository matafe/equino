package com.matafe.equino.controller;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.matafe.equino.dto.AnimalDTO;
import com.matafe.equino.model.Animal;
import com.matafe.equino.model.Gender;
import com.matafe.equino.model.Owner;
import com.matafe.equino.repository.AnimalRepository;
import com.matafe.equino.repository.OwnerRepository;

import jakarta.validation.Valid;

@Controller
public class AnimalController implements MessageSourceAware {

	private MessageSource messageSource;

	@Autowired
	private AnimalRepository animalRepository;

	@Autowired
	private OwnerRepository ownerRepository;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping("/animals")
	public String animals(Model model) {
		List<Animal> animals = this.animalRepository.fetchAll();
		model.addAttribute("animals", animals);
		return "animals";
	}

	@GetMapping("/animal")
	public String animalForm(Animal animal, Model model) {
		List<Owner> owners = this.ownerRepository.findAll();

		model.addAttribute("activePage", "animal");
		model.addAttribute("genders", Gender.values());
		model.addAttribute("owners", owners);

		return "animal";
	}

	@PostMapping("/animal")
	public String saveAnimal(@Valid Animal animal, BindingResult bindingResult, Model model,
			RedirectAttributes attributes, Locale locale) {

		if (bindingResult.hasErrors()) {
			return animalForm(animal, model);
		}

		animalRepository.save(animal);

		attributes.addFlashAttribute("message",
				messageSource.getMessage("animal.saved", new String[] { animal.getName() }, locale));

		return "redirect:/animals";
	}

	@GetMapping(value = "owner/{id}/animals", produces = "application/json")
	public @ResponseBody List<AnimalDTO> getAnimalsFromOwner(@PathVariable Long id) {
		Optional<Owner> ownerOptional = this.ownerRepository.findById(id);
		if (ownerOptional.isPresent()) {
			Owner owner = ownerOptional.get();
			List<Animal> animals = this.animalRepository.findByOwner(owner);
			return animals.stream().map(a -> new AnimalDTO(a.getId(), a.getName())).collect(toList());

		}
		return Collections.emptyList();

	}

}
