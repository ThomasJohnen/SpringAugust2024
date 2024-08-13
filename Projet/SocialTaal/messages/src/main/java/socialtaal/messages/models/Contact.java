package socialtaal.messages.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Contact {

    public enum ContactType {
        ACTIVE,
        CLOSED,
        PENDING
    }

    private int id;

    private String senderPseudo;

    private String receiverPseudo;

    private ContactType status;

}
