package com.Identity.R.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Identity.R.Entities.Contact;
import com.Identity.R.Precedence.LinkPrecedence;
import com.Identity.R.Repo.ContactRepository;

@Service
public class ContactService {
    
    
    @Autowired
    private ContactRepository contactRepository;

    public Map<String, Object> identifyContact(String email, String phoneNumber) {
        List<Contact> existingContacts = contactRepository.findByEmailOrPhoneNumber(email, phoneNumber);

        Map<String, Object> response = new HashMap<>();
        if (existingContacts.isEmpty()) {
            // If no existing contacts found, create a new primary contact
            Contact newContact = new Contact(null, phoneNumber, phoneNumber, null, null);
            newContact.setEmail(email);
            newContact.setPhoneNumber(phoneNumber);
            newContact.setLinkPrecedence(LinkPrecedence.PRIMARY);
            Contact savedContact = contactRepository.save(newContact);

            response.put("contact", mapContactToResponse(savedContact));
        } else {
            // Consolidate existing contacts
            Contact primaryContact = null;
            List<Contact> secondaryContacts = new ArrayList<>();

            for (Contact contact : existingContacts) {
                if (contact.getLinkPrecedence() == LinkPrecedence.PRIMARY) {
                    primaryContact = contact;
                } else {
                    secondaryContacts.add(contact);
                }
            }

            // Update primary contact if necessary
            if (primaryContact != null) {
                if (email != null && !email.equals(primaryContact.getEmail())) {
                    primaryContact.setEmail(email);
                }
                if (phoneNumber != null && !phoneNumber.equals(primaryContact.getPhoneNumber())) {
                    primaryContact.setPhoneNumber(phoneNumber);
                }
                primaryContact = contactRepository.save(primaryContact);
            } else {
                primaryContact = createPrimaryContact(email, phoneNumber);
            }

            response.put("contact", mapContactToResponse(primaryContact, secondaryContacts));
        }
        return response;
    }

    private Contact createPrimaryContact(String email, String phoneNumber) {
        Contact newContact = new Contact(null, phoneNumber, phoneNumber, null, null);
        newContact.setEmail(email);
        newContact.setPhoneNumber(phoneNumber);
        newContact.setLinkPrecedence(LinkPrecedence.PRIMARY);
        return contactRepository.save(newContact);
    }

    private Map<String, Object> mapContactToResponse(Contact primaryContact, List<Contact> secondaryContacts) {
        Map<String, Object> contactMap = new HashMap<>();
        contactMap.put("primaryContactId", primaryContact.getId());
        List<String> emails = new ArrayList<>();
        List<String> phoneNumbers = new ArrayList<>();
        for (Contact contact : secondaryContacts) {
            emails.add(contact.getEmail());
            phoneNumbers.add(contact.getPhoneNumber());
        }
        emails.add(0, primaryContact.getEmail());
        phoneNumbers.add(0, primaryContact.getPhoneNumber());
        contactMap.put("emails", emails);
        contactMap.put("phoneNumbers", phoneNumbers);
        contactMap.put("secondaryContactIds", secondaryContacts.stream().map(Contact::getId).collect(Collectors.toList()));
        return contactMap;
    }

    private Map<String, Object> mapContactToResponse(Contact contact) {
        Map<String, Object> contactMap = new HashMap<>();
        contactMap.put("primaryContactId", contact.getId());
        contactMap.put("emails", Collections.singletonList(contact.getEmail()));
        contactMap.put("phoneNumbers", Collections.singletonList(contact.getPhoneNumber()));
        contactMap.put("secondaryContactIds", Collections.emptyList());
        return contactMap;
    }
}

