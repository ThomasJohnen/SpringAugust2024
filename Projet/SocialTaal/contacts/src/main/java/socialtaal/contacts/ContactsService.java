package socialtaal.contacts;

import org.springframework.stereotype.Service;
import socialtaal.contacts.models.Contact;
import socialtaal.contacts.models.ContactRequest;
import socialtaal.contacts.repository.ContactsRepository;
import socialtaal.contacts.repository.UsersProxy;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class ContactsService {

    private final ContactsRepository repository;
    private final UsersProxy usersProxy;

    public ContactsService(ContactsRepository aNewrepository, UsersProxy usersProxy) {
        this.usersProxy = usersProxy;
        this.repository = aNewrepository;
    }

    /**
     * Get all contacts of a user
     * @param senderPseudo the pseudo of the sender
     * @param stateContact the state of the contact
     * @return a list of all contacts
     */
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

    /**
     * Get a contact with the sender and receiver pseudo
     * @param senderPseudo
     * @param receiverPseudo
     * @return
     */
    public Contact getContact(String senderPseudo, String receiverPseudo) {
        Contact contactGetted = repository.findBySenderPseudoAndReceiverPseudo(senderPseudo, receiverPseudo);
        if (contactGetted == null) {
            return null;
        }
        return contactGetted;
    }

    /**
     * Save a contact
     * @param contact
     * @return the saved contact
     */
    public Contact save(ContactRequest contact) {
        if( usersProxy.getUser(contact.getReceiverPseudo()) == null || usersProxy.getUser(contact.getSenderPseudo()) == null )
            return null;
        Contact newContact = new Contact();
        newContact.setSenderPseudo(contact.getSenderPseudo());
        newContact.setReceiverPseudo(contact.getReceiverPseudo());
        newContact.setStatus(Contact.ContactType.PENDING);
        return repository.save(newContact);
    }

    public Contact updateContact(String senderPseudo, String receiverPseudo, String status) {
        Contact contact = getContact(senderPseudo, receiverPseudo);
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
        return repository.save(contact);
    }

    /**
     * Get all contacts
     * @return a list of all contacts
     */
    public List<Contact> getAllContacts() {
        Iterable<Contact> allContacts = repository.findAll();
        return StreamSupport.stream(allContacts.spliterator(), false).toList();
    }
}
