package socialtaal.contacts.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import socialtaal.contacts.models.Contact;

import java.util.List;

@Repository
public interface ContactsRepository extends CrudRepository<Contact, String> {

    List<Contact> findBySenderPseudoAndStatus(String senderPseudo, Contact.ContactType status);
}
