package socialtaal.contacts.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class  ContactRequest {
    private String senderPseudo;
    private String receiverPseudo;
}