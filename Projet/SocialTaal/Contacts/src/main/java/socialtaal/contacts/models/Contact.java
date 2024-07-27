package socialtaal.contacts.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "contacts")
public class Contact {

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

    @Column(nullable = false)
    private ContactType status;

}
