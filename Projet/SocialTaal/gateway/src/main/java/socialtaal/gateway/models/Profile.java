package socialtaal.gateway.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Profile {

    private String biography;

    private boolean contactable = false;
}
