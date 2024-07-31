package socialtaal.contacts.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@IdClass(Contact.class)
@Entity(name = "contacts")
public class Contact implements Serializable {

    public enum ContactType {
        ACTIVE,
        CLOSED,
        PENDING
    }

     @Id
     @Column
    private String senderPseudo;

    @Id
    @Column
    private String receiverPseudo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContactType status;

}
