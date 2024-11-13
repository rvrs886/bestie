package com.rvrs.bestie.security.dto;

import com.rvrs.bestie.security.domain.PersonalInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CustomerDto(@Valid @NotNull RegisterCredentials registerCredentials, @Valid @NotNull PersonalInfo personalInfo, byte[] image) {
}
