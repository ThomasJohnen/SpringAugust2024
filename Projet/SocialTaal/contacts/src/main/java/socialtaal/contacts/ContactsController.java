package socialtaal.contacts;


import feign.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import socialtaal.contacts.models.Contact;
import socialtaal.contacts.models.ContactRequest;
import socialtaal.contacts.repository.UsersProxy;

import java.util.List;

@RestController
public class ContactsController {

    private final ContactsService service;
    private final UsersProxy usersProxy;

    public ContactsController(ContactsService aNewservice, UsersProxy aNewusersProxy) {
        this.service = aNewservice;
        this.usersProxy = aNewusersProxy;
    }

    /**
     * Add a contact
     * @param contactRequest the contact to add with the sender and receiver pseudo
     * @return the created contact if it doesn't exist, else return a 400 status
     */
    @PostMapping("/contact")
    public ResponseEntity<Contact> addContact(@RequestBody ContactRequest contactRequest) {
        if(contactRequest.getSenderPseudo().equals(contactRequest.getReceiverPseudo()) || usersProxy.getUser(contactRequest.getSenderPseudo()) == null || usersProxy.getUser(contactRequest.getReceiverPseudo()) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Contact newContact = new Contact();
        newContact.setSenderPseudo(contactRequest.getSenderPseudo());
        newContact.setReceiverPseudo(contactRequest.getReceiverPseudo());
        newContact.setStatus(Contact.ContactType.PENDING);
        Contact savedContact = service.save(newContact);
        return new ResponseEntity<>(savedContact, HttpStatus.CREATED);
    }

    /**
     * Get all contacts
     * @param senderPseudo the pseudo of the sender
     * @param stateContact the state of the contact
     * @return a list of all contacts
     */
    @GetMapping("/getList/{senderPseudo}/{stateContact}")
    public ResponseEntity<List<Contact>> getContacts(@PathVariable String senderPseudo, @PathVariable String stateContact) {
        System.out.println("senderPseudo: " + senderPseudo + " stateContact: " + stateContact);
        List<Contact> contacts = service.getContacts(senderPseudo, stateContact);
        if (contacts == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    /**
     * Get a contact by sender and receiver pseudo
     * @param senderPseudo the pseudo of the sender
     * @param receiverPseudo the pseudo of the receiver
     * @return the contact if it exists, else return a 404 status
     */
    @GetMapping("/{senderPseudo}/{receiverPseudo}")
    public ResponseEntity<Contact> getContact(@PathVariable String senderPseudo, @PathVariable String receiverPseudo) {
        System.out.println("On est dans la méthode getContact");
        Contact contact = service.getContact(senderPseudo, receiverPseudo);
        if (contact == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    /**
     * Modify a contact
     * @param senderPseudo the pseudo of the sender
     * @param receiverPseudo the pseudo of the receiver
     * @param status the state of the contact
     * @return the modified contact if it exists, else return a 404 status
     */
    @PatchMapping("/{senderPseudo}/{receiverPseudo}/{status}")
    public ResponseEntity<Contact> modifyContact(@PathVariable String senderPseudo, @PathVariable String receiverPseudo, @PathVariable String status) {
        System.out.println("senderPseudo: " + senderPseudo + " stateContact: " + status);
        Contact contactModified = service.updateContact(senderPseudo, receiverPseudo, status);
        if (contactModified == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(contactModified, HttpStatus.OK);

    }

}
