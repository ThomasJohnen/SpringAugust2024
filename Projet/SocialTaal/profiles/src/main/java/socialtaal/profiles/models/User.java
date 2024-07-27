package socialtaal.profiles.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
}
