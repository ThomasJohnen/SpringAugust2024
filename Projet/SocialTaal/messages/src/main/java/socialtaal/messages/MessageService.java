package socialtaal.messages;

import org.springframework.stereotype.Service;
import socialtaal.messages.models.Message;
import socialtaal.messages.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }
}
