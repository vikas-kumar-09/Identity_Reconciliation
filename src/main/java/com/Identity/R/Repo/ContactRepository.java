package com.Identity.R.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Identity.R.Entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long>{
    
    List<Contact> findByEmailOrPhoneNumber(String email, String phoneNumber);
} 
