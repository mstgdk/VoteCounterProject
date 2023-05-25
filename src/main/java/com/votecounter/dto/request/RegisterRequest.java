package com.votecounter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Size(max=50)
    @NotBlank(message="Please provide your First Name")
    // @NotBlank = charSequence.toString().trim().length()>0
    private String firstName;

    @Size(max=50)
    @NotBlank(message="Please provide your Last Name")
    private String lastName;

    @Size(min=5 ,max=80)
    @Email(message = "Please provide valid e-mail")
    private String citNumber;

    @Size(min=4 , max=20, message="Please provide Correct Size of Password")
    @NotBlank(message="Please provide your password")
    private String password;
}
