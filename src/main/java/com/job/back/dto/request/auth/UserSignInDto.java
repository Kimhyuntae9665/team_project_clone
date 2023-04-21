package com.job.back.dto.request.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSignInDto {
    @NotBlank
    @Email
    @Length(max = 20)
    private String userEmail;
    @NotBlank
    @Length(min = 8, max = 20)
    private String userPassword;
}