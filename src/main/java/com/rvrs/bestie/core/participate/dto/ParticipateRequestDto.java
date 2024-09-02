package com.rvrs.bestie.core.participate.dto;

import jakarta.validation.constraints.NotBlank;

public record ParticipateRequestDto(@NotBlank String message) {
}
