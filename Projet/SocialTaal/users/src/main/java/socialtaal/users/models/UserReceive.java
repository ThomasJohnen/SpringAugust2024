package socialtaal.users.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserReceive {


    private String pseudo;

    private Genders gender;

    private String birthdate;

    private String birthCountry;

    private String motherTongue;

    private boolean disable = false;

    private String biography;

    private boolean contactable;

    private String password;
}
