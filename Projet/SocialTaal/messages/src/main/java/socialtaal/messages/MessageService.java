package socialtaal.messages;

import org.springframework.stereotype.Service;
import socialtaal.messages.models.Message;
import socialtaal.messages.repository.MessageRepository;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
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
}
