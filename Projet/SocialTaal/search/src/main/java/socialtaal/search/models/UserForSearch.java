package socialtaal.search.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserForSearch {

    private String pseudo;


    private Genders gender;

    private String birthdate;

    private String birthCountry;

    private String motherTongue;

    private boolean disable = false;

    private String biography;

    private boolean contactable;
}
