package socialtaal.contacts.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import socialtaal.contacts.models.Contact;

import java.util.List;

@Repository
public interface ContactsRepository extends CrudRepository<Contact, Integer> {

    @Query("SELECT c FROM contacts c WHERE (c.senderPseudo = :senderPseudo OR c.receiverPseudo= :senderPseudo) AND c.status = :status")
    List<Contact> findBySenderPseudoAndStatus(String senderPseudo, Contact.ContactType status);

    @Query("SELECT c FROM contacts c WHERE (c.senderPseudo = :senderPseudo OR c.receiverPseudo= :senderPseudo)")
    List<Contact> findBySenderPseudo(String senderPseudo);

    @Query("SELECT c FROM contacts c WHERE (c.senderPseudo = :senderPseudo AND c.receiverPseudo = :receiverPseudo) OR (c.senderPseudo = :receiverPseudo AND c.receiverPseudo = :senderPseudo)")
    Contact findBySenderPseudoAndReceiverPseudo(String senderPseudo, String receiverPseudo);
}
