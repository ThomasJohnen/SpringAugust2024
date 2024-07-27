package socialtaal.contacts;

import org.springframework.stereotype.Service;
import socialtaal.contacts.models.Contact;
import socialtaal.contacts.repository.ContactsRepository;

import java.util.List;

@Service
public class ContactsService {

    private final ContactsRepository repository;

    public ContactsService(ContactsRepository aNewrepository) {
        this.repository = aNewrepository;
    }

    public List<Contact> getContacts(String senderPseudo, Contact.ContactType stateContact) {
        if(stateContact.equals(Contact.ContactType.PENDING)) {
            return repository.findBySenderPseudoAndStatus(senderPseudo, Contact.ContactType.PENDING);
        } else if( stateContact.equals(Contact.ContactType.ACTIVE))
            return repository.findBySenderPseudoAndStatus(senderPseudo, Contact.ContactType.ACTIVE);
        else return null;

    }
    public Contact getContact(String senderPseudo, String receiverPseudo) {
        return repository.findById(senderPseudo + receiverPseudo).orElse(null);
    }

    public Contact save(Contact contact) {
        return repository.save(contact);
    }

    public Contact updateContact(String senderPseudo, String receiverPseudo, Contact.ContactType status) {
        Contact contact = getContact(senderPseudo, receiverPseudo);
        if (contact == null) {
            return null;
        }
        if(status.equals(Contact.ContactType.ACTIVE))
            contact.setStatus(Contact.ContactType.ACTIVE);
        else
            contact.setStatus(Contact.ContactType.CLOSED);
        contact.setStatus(status);
        return save(contact);
    }
}
