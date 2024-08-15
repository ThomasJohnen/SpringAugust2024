package socialtaal.messages;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import socialtaal.messages.models.Contact;
import socialtaal.messages.models.Message;
import socialtaal.messages.models.MessagePosted;

import java.util.List;

@Controller
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Add a message to the database
     * @param message the message to add
     * @return the added message
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> addMessage(@RequestBody MessagePosted message) {
        if(message == null || message.getMessage() == null || message.getSenderPseudo() == null || message.getReceiverPseudo() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        ResponseEntity<Contact> contactResponse = messageService.getContact(message.getSenderPseudo(), message.getReceiverPseudo());

        if (contactResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (contactResponse.getBody() == null || !contactResponse.getBody().getStatus().toString().equals("ACTIVE")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Message messageToPost = new Message();
        messageToPost.setMessage(message.getMessage());
        messageToPost.setSenderPseudo(message.getSenderPseudo());
        messageToPost.setReceiverPseudo(message.getReceiverPseudo());
        messageToPost.setTimestamp(String.valueOf(System.currentTimeMillis()));
        Message savedMessage = messageService.save(messageToPost);
        if(savedMessage == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }

    /**
     * Get all messages for a given pseudo
     * @param pseudo
     * @return the list of messages
     */
    @GetMapping("/messages/{pseudo}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String pseudo) {
        if(pseudo == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(messageService.getUser(pseudo) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Message> messages = messageService.getMessages(pseudo);
        if(messages == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
