package com.Identity.R.Entities;

import com.Identity.R.Precedence.LinkPrecedence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// We can use lombok dependency to remove boilerplate code
@Entity
public class Contact{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    private Long linkedId;

    // @Enumerated(EnumType.String)
    private LinkPrecedence linkPrecedence;

    public Contact(Long id, String email, String phoneNumber, Long linkedId, LinkPrecedence linkPrecedence) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.linkedId = linkedId;
        this.linkPrecedence = linkPrecedence;
    }
    
   
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getLinkedId() {
        return this.linkedId;
    }

    public void setLinkedId(Long linkedId) {
        this.linkedId = linkedId;
    }

    public LinkPrecedence getLinkPrecedence() {
        return this.linkPrecedence;
    }

    public void setLinkPrecedence(LinkPrecedence linkPrecedence) {
        this.linkPrecedence = linkPrecedence;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", linkedId='" + getLinkedId() + "'" +
            ", linkPrecedence='" + getLinkPrecedence() + "'" +
            "}";
    }
}
