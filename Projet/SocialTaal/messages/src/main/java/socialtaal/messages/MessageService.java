package socialtaal.messages;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import socialtaal.messages.models.Contact;
import socialtaal.messages.models.Message;
import socialtaal.messages.models.User;
import socialtaal.messages.repository.ContactProxy;
import socialtaal.messages.repository.MessageRepository;
import socialtaal.messages.repository.UsersProxy;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UsersProxy usersProxy;

    private final ContactProxy contactProxy;

    public MessageService(MessageRepository messageRepository, UsersProxy usersProxy, ContactProxy contactProxy) {
        this.usersProxy = usersProxy;
        this.messageRepository = messageRepository;
        this.contactProxy = contactProxy;
    }

    /**
     * Save a message in the database
     * @param messageEchange
     * @return
     */
    public Message save(Message messageEchange) {
        return messageRepository.save(messageEchange);

    }

    /**
     * Get all messages for a given pseudo
     * @param pseudo
     * @return the list of messages
     */
    public List<Message> getMessages(String pseudo) {
        Iterable<Message> messages =  messageRepository.findByReceiverPseudo(pseudo);
        return StreamSupport.stream(messages.spliterator(), false).toList();
    }

    public ResponseEntity<User> getUser(String pseudo){
        return usersProxy.getUser(pseudo);
    }

    public ResponseEntity<Contact> getContact(String senderPseudo, String receiverPseudo) {
        try {
            return contactProxy.getContact(senderPseudo, receiverPseudo);
        } catch (FeignException.Unauthorized e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (FeignException.NotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (FeignException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
