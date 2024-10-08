package socialtaal.gateway.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Message {

    private String senderPseudo;


    private String receiverPseudo;

    private String message;

    private String timestamp;

}
