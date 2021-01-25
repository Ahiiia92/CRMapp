package com.crm.app.services;

import com.crm.app.models.Contact;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("contactService")
public interface ContactService {
    List<Contact> getAllContacts();

    Contact createContact(Contact contact);

    Contact getContactById(Integer contactId);

    Contact updateContact(Integer id, Contact contactDetails);

    void deleteContact(Integer id);
}
