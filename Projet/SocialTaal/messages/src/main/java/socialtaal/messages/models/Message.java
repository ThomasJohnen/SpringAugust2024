package socialtaal.messages.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String senderPseudo;

    @Column(nullable = false)
    private String receiverPseudo;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String timestamp;
}