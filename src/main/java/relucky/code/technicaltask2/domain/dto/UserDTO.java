package relucky.code.technicaltask2.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;


public record UserDTO (
        @Size(min = 2, max = 20, message = "min size of name is 2, max is 20") String name,
        @Email(message = "should be email") String email,
        @Max(value = 120) @Min(value = 0) Integer age,
        @Size(max = 30, message = "Max value is 30") String password){
}