package com.rvrs.bestie.security.dto;

import jakarta.validation.constraints.NotEmpty;

public record RegisterCredentials(@NotEmpty String username, @NotEmpty String password, @NotEmpty String repeatedPassword) {
}
