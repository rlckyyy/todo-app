package relucky.code.technicaltask2.common.enums;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenType {
    ACCESS(60, SignatureAlgorithm.HS384), // 1 hour
    REFRESH(1440, SignatureAlgorithm.HS512); //1 day
    private final Integer expirationMinute;
    private final SignatureAlgorithm algorithm;
}
