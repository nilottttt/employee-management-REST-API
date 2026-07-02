package ems_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private Long id;
    @NotBlank(message = "First name is required and cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name field is required and cannot be blank")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address format.")
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
