package relucky.code.technicaltask2.common.enums;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenType {
    ACCESS(Integer.parseInt(System.getenv("EXPIRATIONMINUTEACCESS")), SignatureAlgorithm.HS384),
    REFRESH(Integer.parseInt(System.getenv("EXPIRATIONMINUTEREFRESH")), SignatureAlgorithm.HS512);

    private final Integer expirationMinute;
    private final SignatureAlgorithm algorithm;

}

