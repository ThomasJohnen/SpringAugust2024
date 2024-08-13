package socialtaal.messages.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    public enum Gender {
        MALE,
        FEMALE;
    }

    private String pseudo;

    private Gender gender;

    private String birthdate;

    private String birthCountry;

    private String motherTongue;

    private boolean disable = false;

    private String biography;

    private boolean contactable = false;
}
