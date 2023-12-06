package com.example.agenda;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Transactional
public class ContactApiController {

    final ContactRepository contactRepository;

    @GetMapping("/")
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @PostMapping("/add")
    public Contact doAddContact(AddContact dto){
        Contact contact = new Contact();
        BeanUtils.copyProperties(dto, contact);

        contactRepository.save(contact);
        return contact;
    }

    @PostMapping("edit")
    public Contact doEditContact(@Valid UpdateContact dto, BindingResult result){
        if(result.hasErrors()){
            throw new RuntimeException("Bad request");
        }
        Contact c = contactRepository.getReferenceById(dto.getId());
        BeanUtils.copyProperties(dto,c);
        contactRepository.save(c);
        //return "redirect:/edit?id=" + c.getId();
        return c;
    }

}
