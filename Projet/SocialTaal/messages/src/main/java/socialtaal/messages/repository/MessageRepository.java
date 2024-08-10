package socialtaal.messages.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import socialtaal.messages.models.Message;


@Repository
public interface MessageRepository extends CrudRepository<Message, String> {

    /**
     * Get all messages for a given pseudo
     * @param pseudo
     * @return the list of messages ordered by timestamp
     */
    @Query("SELECT m FROM message m WHERE (m.receiverPseudo = :pseudo OR m.senderPseudo = :pseudo) ORDER BY m.timestamp ")
    Iterable<Message> findByReceiverPseudo(String pseudo);

}
