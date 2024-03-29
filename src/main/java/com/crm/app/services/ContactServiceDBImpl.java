package com.crm.app.services;

import com.crm.app.exceptions.ContactNotFoundException;
import com.crm.app.models.Contact;
import com.crm.app.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ContactServiceDBImpl implements ContactService {
    private ContactRepository contactRepository;

    public ContactServiceDBImpl(@Qualifier("contactRepository") ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact getContactById(Integer contactId) {
        Optional<Contact> optContact = contactRepository.findById(contactId);
        if(optContact.isPresent())
            return optContact.get();
        else
            throw new ContactNotFoundException(contactId);
    }

    @Override
    public Contact updateContact(Integer id, Contact contactDetails) {
        Optional<Contact> optContact = contactRepository.findById(id);
        if(optContact.isPresent()) {
            optContact.get().setFirstname(contactDetails.getFirstname());
            optContact.get().setLastname(contactDetails.getLastname());
            optContact.get().setAddress(contactDetails.getAddress());
            optContact.get().setUser(contactDetails.getUser());
            optContact.get().setContact_status(contactDetails.getContact_status());
            optContact.get().setEmail(contactDetails.getEmail());
            contactRepository.save(optContact.get());
            return optContact.get();
        } else {
            throw new ContactNotFoundException(id);
        }
    }

    @Override
    public void deleteContact(Integer id) {
        Optional<Contact> contactToDelete = contactRepository.findById(id);
        contactRepository.delete(contactToDelete.get());
    }
}
