package com.ragnar.MySchoolManagement.user;

public record UserUpdateRequest(
        String firstname,
        String lastname,
        String email,
        Integer age
) {
}
