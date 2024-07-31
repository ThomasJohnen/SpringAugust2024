package socialtaal.contacts;

import org.springframework.stereotype.Service;
import socialtaal.contacts.models.Contact;
import socialtaal.contacts.repository.ContactsRepository;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class ContactsService {

    private final ContactsRepository repository;

    public ContactsService(ContactsRepository aNewrepository) {
        this.repository = aNewrepository;
    }

    public List<Contact> getContacts(String senderPseudo, String stateContact) {
        System.out.println("On est dans le service getContacts");
        Contact.ContactType contactType;
        try {
             contactType = Contact.ContactType.valueOf(stateContact.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid stateContact value: " + stateContact);
            return null;
        }

        if(contactType == Contact.ContactType.PENDING) {
            System.out.println("On essaie d'avoir la liste PENDING");
            return repository.findBySenderPseudoAndStatus(senderPseudo, Contact.ContactType.PENDING);
        } else if( contactType == Contact.ContactType.ACTIVE) {
            System.out.println("On essaie d'avoir la liste ACIVE");
            return repository.findBySenderPseudoAndStatus(senderPseudo, Contact.ContactType.ACTIVE);
        }

        else {
            System.out.println("C'est la merde");
            return null;
        }

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

    public List<Contact> getAllContacts() {
        Iterable<Contact> allContacts = repository.findAll();
        return StreamSupport.stream(allContacts.spliterator(), false).toList();
    }
}
