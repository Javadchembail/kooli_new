package com.kooli.app.kooli.payloads.requests;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    private String mobile;
    private String email;
    private String firstname;
    private String lastname;
    private String gender;
    private String dob;
    private String companyname;
    private String category;
    private String subcategory;
    private String title;
    private String description;

}
