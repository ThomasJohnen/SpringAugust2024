package socialtaal.authentication.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnsafeCredentials {
    private String pseudo;
    private String password;

    public SafeCredentials makeSafe(String hashedPassword) {
        return new SafeCredentials(pseudo, hashedPassword);
    }

    public boolean invalid() {
        return pseudo == null || pseudo.isBlank() || password == null || password.isBlank();
    }
}
