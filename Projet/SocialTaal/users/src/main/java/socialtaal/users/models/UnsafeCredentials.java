package socialtaal.users.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnsafeCredentials {
    private String pseudo;
    private String password;

}
