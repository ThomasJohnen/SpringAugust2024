package socialtaal.users.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Profile {


    private String biography;

    private boolean contactable = false;

}
