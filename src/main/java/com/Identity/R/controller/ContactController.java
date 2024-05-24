package com.Identity.R.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.Identity.R.Services.ContactService;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/identify")
    public ResponseEntity<Map<String, Object>> identifyContact(@RequestBody Map<String, Object> requestData) {
        String email = (String) requestData.get("email");
        String phoneNumber = (String) requestData.get("phoneNumber");
        Map<String, Object> response = contactService.identifyContact(email, phoneNumber);
        return ResponseEntity.ok(response);
    }
}