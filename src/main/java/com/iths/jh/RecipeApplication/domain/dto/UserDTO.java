package com.iths.jh.RecipeApplication.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String profileName;
    private String password;
    private LocalDate accountCreated;
    private String email;
}
