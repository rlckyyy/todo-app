package relucky.code.technicaltask2.common.payload.response;


public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
