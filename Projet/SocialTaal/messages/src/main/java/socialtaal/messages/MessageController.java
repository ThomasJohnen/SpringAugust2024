package socialtaal.messages;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import socialtaal.messages.models.Messages;

@Controller
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/messages")
    public ResponseEntity<Messages> addMessage(@RequestBody Messages message) {
        if(message == null || message.g) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Message savedMessage = messageService.save(message);
        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }
}
