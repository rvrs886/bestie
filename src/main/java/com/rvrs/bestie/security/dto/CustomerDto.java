package com.rvrs.bestie.security.dto;

import com.rvrs.bestie.security.domain.PersonalInfo;

//TODO: Create process of account creation divided to steps, e.g.: first step - personal info, second step - password data, etc...
public record CustomerDto(PersonalInfo personalInfo, byte[] image) {
}
