package socialtaal.messages.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "messages")
public class Message {

    @Id
    @Column
    private String senderPseudo;

    @Id
    @Column
    private String receiverPseudo;

    @Column
    private String message;

    @Column
    private String timestamp;

}
