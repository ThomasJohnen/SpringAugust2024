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
        Contact.ContactType contactType;
        try {
             contactType = Contact.ContactType.valueOf(stateContact.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid stateContact value: " + stateContact);
            return null;
        }

        if(contactType == Contact.ContactType.PENDING) {
            return repository.findBySenderPseudoAndStatus(senderPseudo, Contact.ContactType.PENDING);
        } else if( contactType == Contact.ContactType.ACTIVE) {
            return repository.findBySenderPseudoAndStatus(senderPseudo, Contact.ContactType.ACTIVE);
        }

        else {
            return null;
        }

    }
    public Contact getContact(String senderPseudo, String receiverPseudo) {
        Contact contactGetted = repository.findBySenderPseudoAndReceiverPseudo(senderPseudo, receiverPseudo);
        if (contactGetted == null) {
            return null;
        }
        return contactGetted;
    }

    public Contact save(Contact contact) {
        return repository.save(contact);
    }

    public Contact updateContact(String senderPseudo, String receiverPseudo, String status) {
        Contact contact = getContact(senderPseudo, receiverPseudo);
        System.out.println("On est dans la méthode updateContact du service et le user vaut : " + contact);
        if (contact == null) {
            return null;
        }

        Contact.ContactType contactType;
        try {
            contactType = Contact.ContactType.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid stateContact value: " + status);
            return null;
        }
        if(contactType == Contact.ContactType.ACTIVE)
            contact.setStatus(Contact.ContactType.ACTIVE);
        else
            contact.setStatus(Contact.ContactType.CLOSED);
        return save(contact);
    }

    public List<Contact> getAllContacts() {
        Iterable<Contact> allContacts = repository.findAll();
        return StreamSupport.stream(allContacts.spliterator(), false).toList();
    }
}
