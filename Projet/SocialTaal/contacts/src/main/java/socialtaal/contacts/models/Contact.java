package socialtaal.contacts.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "contacts")
public class Contact{

    public enum ContactType {
        ACTIVE,
        CLOSED,
        PENDING
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


     @Column(nullable = false)
    private String senderPseudo;


    @Column(nullable = false)
    private String receiverPseudo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContactType status;

}
