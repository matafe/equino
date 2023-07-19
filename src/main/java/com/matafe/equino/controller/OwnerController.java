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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.matafe.equino.dto.AnimalDTO;
import com.matafe.equino.model.Animal;
import com.matafe.equino.model.Owner;
import com.matafe.equino.repository.AnimalRepository;
import com.matafe.equino.repository.OwnerRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/owners")
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

	@GetMapping
	public String owners(Model model) {
		List<Owner> owners = this.ownerRepository.findAll();
		model.addAttribute("owners", owners);
		return "owners/owners";
	}

	@GetMapping("add")
	public String ownerForm(Owner owner) {
		return "owners/ownerForm";
	}

	@PostMapping("add")
	public String saveOwner(@Valid Owner owner, BindingResult bindingResult, Model model, RedirectAttributes attributes,
			Locale locale) {

		if (bindingResult.hasErrors()) {
			return "owners/ownerForm";
		}

		List<Owner> founds = ownerRepository.findByCpf(owner.getCpf());
		if (!founds.isEmpty()) {
			bindingResult.rejectValue("cpf", "error.cpf.exists");
			return "owners/ownerForm";
		}
		founds = ownerRepository.findByEmailIgnoreCase(owner.getEmail());
		if (!founds.isEmpty()) {
			bindingResult.rejectValue("email", "error.email.exists");
			return "owners/ownerForm";
		}

		ownerRepository.save(owner);

		attributes.addFlashAttribute("message",
				messageSource.getMessage("owner.saved", new String[] { owner.getName() }, locale));
		attributes.addFlashAttribute("owner", owner);

		return "redirect:/owners";
	}

	@GetMapping("{id}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes attributes, Locale locale) {

		Optional<Owner> owner = ownerRepository.findById(id);
		if (owner.isPresent() && (!owner.get().getAnimals().isEmpty())) {
			attributes.addFlashAttribute("errorMessage",
					messageSource.getMessage("owners.delete.validation.animals", new String[] { owner.get().getName() }, locale));
			return "redirect:/owners";
		}

		ownerRepository.deleteById(id);
		attributes.addFlashAttribute("message", messageSource.getMessage("record.deleted", new String[0], locale));

		return "redirect:/owners";
	}

	@GetMapping(value = "{id}/animals", produces = "application/json")
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
