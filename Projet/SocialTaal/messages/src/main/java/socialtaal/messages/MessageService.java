package socialtaal.messages;

import org.springframework.stereotype.Service;
import socialtaal.messages.models.Message;
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
        if(contactProxy.getContact(messageEchange.getSenderPseudo(), messageEchange.getReceiverPseudo()).getBody() != null){
            return messageRepository.save(messageEchange);
        } return null;

    }

    /**
     * Get all messages for a given pseudo
     * @param pseudo
     * @return the list of messages
     */
    public List<Message> getMessages(String pseudo) {
        if(usersProxy.getUser(pseudo) == null){
            return null;
        }
        Iterable<Message> messages =  messageRepository.findByReceiverPseudo(pseudo);
        return StreamSupport.stream(messages.spliterator(), false).toList();
    }
}
