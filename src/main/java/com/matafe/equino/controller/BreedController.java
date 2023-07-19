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

import com.matafe.equino.model.Breed;
import com.matafe.equino.repository.BreedRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/breeds")
public class BreedController implements MessageSourceAware {

	private MessageSource messageSource;

	@Autowired
	private BreedRepository breedRepository;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping
	public String findAll(Model model) {
		List<Breed> breeds = this.breedRepository.findAll();
		model.addAttribute("breeds", breeds);
		return "breeds/breeds";
	}

	@GetMapping("add")
	public String addForm(Breed breed) {
		return "breeds/breedForm";
	}

	@PostMapping("add")
	public String save(@Valid Breed breed, BindingResult bindingResult, Model model, RedirectAttributes attributes,
			Locale locale) {

		if (bindingResult.hasErrors()) {
			return "breeds/breedForm";
		}

		List<Breed> breeds = breedRepository.findByNameIgnoreCase(breed.getName());

		if (!breeds.isEmpty()) {
			bindingResult.rejectValue("name", "breed.validation.name.exists");
			return "breeds/breedForm";
		}

		breedRepository.save(breed);

		attributes.addFlashAttribute("message",
				messageSource.getMessage("breed.saved", new String[] { breed.getName() }, locale));
		attributes.addFlashAttribute("breed", breed);

		return "redirect:/breeds";
	}

	@GetMapping("{id}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes attributes, Locale locale) {

		Optional<Breed> breedOptional = breedRepository.findById(id);
		if (breedOptional.isPresent()) {
			Breed breed = breedOptional.get();

			if (!breed.getAnimals().isEmpty()) {
				attributes.addFlashAttribute("errorMessage", messageSource.getMessage(
						"breeds.delete.validation.animals", new String[] { breedOptional.get().getName() }, locale));
				return "redirect:/breeds";
			}
		}

		breedRepository.deleteById(id);
		attributes.addFlashAttribute("message", messageSource.getMessage("record.deleted", new String[0], locale));

		return "redirect:/breeds";
	}

}
