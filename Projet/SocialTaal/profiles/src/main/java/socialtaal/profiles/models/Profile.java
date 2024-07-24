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
@Entity(name = "profiles")
public class Profile {

    @Id
    @Column(nullable = false)
    private String username;

    @Column
    private String biography;

    @Column
    private boolean contactable;

}
