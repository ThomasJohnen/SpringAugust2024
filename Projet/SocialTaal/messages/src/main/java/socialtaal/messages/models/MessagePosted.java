package socialtaal.messages.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessagePosted {

    private String senderPseudo;

    private String receiverPseudo;

    private String message;
}
