package com.kooli.app.kooli.payloads.requests;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String username;
    private String mobile;
    private String email;
    private String password;
    private String status;
    private String type;
    private String firstname;
    private String lastname;
    private String gender;
    private String dob;
    private String companyname;
    private String category;
    private String subcategory;
    private String image;
    private String address;
    private String title;
    private String description;
    private Boolean verifyemail;
    private Boolean verifymobile; 

}
