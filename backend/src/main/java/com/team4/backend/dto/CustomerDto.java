package com.team4.backend.dto;

import com.team4.backend.entities.Customer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link Customer}
 */
public record CustomerDto(Integer id, @NotNull @Size(max = 255) String name, @NotNull @Size(max = 255) String email,
                          @NotNull @Size(max = 15) String phone) implements Serializable {
}