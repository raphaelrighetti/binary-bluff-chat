package com.raphaelrighetti.binarybluff.chat.dto;

import jakarta.validation.constraints.NotNull;

public record MessageDTO(@NotNull String content) {

}
