package socialtaal.users.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "Users")
public class User {


    @Id
    @Column(nullable = false)
    private String pseudo;

    @Column(nullable = false)
    private Genders gender;

    @Column(nullable = false)
    private String birthdate;

    @Column(nullable = false)
    private String birthCountry;

    @Column(nullable = false)
    private String motherTongue;

    @Column (nullable = false)
    private boolean disable = false;

    @Column
    private String biography;

    @Column
    private boolean contactable = false;


}
