package relucky.code.technicaltask2.common.enums;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@AllArgsConstructor
public enum TokenType {
    ACCESS(Integer.parseInt(System.getenv("EXPIRATIONMINUTEACCESS")), SignatureAlgorithm.HS384),
    REFRESH(Integer.parseInt(System.getenv("EXPIRATIONMINUTEREFRESH")), SignatureAlgorithm.HS512);

    private final Integer expirationMinute;
    private final SignatureAlgorithm algorithm;

}

