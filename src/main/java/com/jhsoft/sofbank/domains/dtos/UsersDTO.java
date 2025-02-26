package com.jhsoft.sofbank.domains.dtos;

import lombok.*;

@Getter
@Setter
public class UsersDTO {

    private String identification;
    private String name;
    private String lastName;
    private String email;

    public UsersDTO(String number, String john, String doe, String mail) {
    }
}
