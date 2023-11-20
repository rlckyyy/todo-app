package relucky.code.technicaltask2.common.payload.request;

public record RegistrationRequest(
        String name,
        String email,
        Integer age,
        String password
) {
}
