package com.example.agenda;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
//@Transactional(isolation = )
public class ContactController {

    final ContactRepository contactRepository;

    @GetMapping("/")
    public String getAllContacts(Model model) {
        var contacte = contactRepository.findAll();
        model.addAttribute("contacts", contacte);
        return "index";
    }

    @GetMapping("/add")
    public String addContact(){
        return "add";
    }

    @PostMapping("/add")
    public String doAddContact(AddContact dto){
        Contact contact = new Contact();
        BeanUtils.copyProperties(dto, contact);

        contactRepository.save(contact);
        return "redirect:/"; //redirect catre homepage
    }

    @GetMapping("/edit")
    public String editContact(@RequestParam Long id, Model model){
        model.addAttribute("contact", contactRepository.getReferenceById(id));
        return "edit";
    }

    @PostMapping("edit")
    public String doEditContact(@Valid @ModelAttribute("contact") UpdateContact dto, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("eroare", "invalid data");
            return "edit";
        }
        Contact c = contactRepository.getReferenceById(dto.getId());
        BeanUtils.copyProperties(dto,c);
        contactRepository.save(c);
        //return "redirect:/edit?id=" + c.getId();
        return "redirect:/";
    }

}
