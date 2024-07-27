package socialtaal.contacts;


import feign.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import socialtaal.contacts.models.Contact;

import java.util.List;

@Controller
public class ContactsController {

    private final ContactsService service;

    public ContactsController(ContactsService aNewservice) {
        this.service = aNewservice;
    }

    @PostMapping
    public ResponseEntity<Contact> addContact(@RequestBody String senderPseudo, @RequestBody String receiverPseudo) {
        if(senderPseudo.equals(receiverPseudo)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Contact newContact = new Contact();
        newContact.setSenderPseudo(senderPseudo);
        newContact.setReceiverPseudo(receiverPseudo);
        newContact.setStatus(Contact.ContactType.PENDING);
        Contact savedContact = service.save(newContact);
        return new ResponseEntity<>(savedContact, HttpStatus.CREATED);
    }

    @GetMapping("/{senderPseudo}/{stateContact}}")
    public ResponseEntity<List<Contact>> getContacts(@PathVariable String senderPseudo, @PathVariable Contact.ContactType stateContact) {
        List<Contact> contacts = service.getContacts(senderPseudo, stateContact);
        if (contacts == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @PatchMapping("/{senderPseudo}/{receiverPseudo}")
    public ResponseEntity<Contact> modifyContact(@PathVariable String senderPseudo, @PathVariable String receiverPseudo, @RequestBody Contact.ContactType status) {
        Contact contact = service.getContact(senderPseudo, receiverPseudo);
        if (contact == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Contact contactModified = service.updateContact(senderPseudo, receiverPseudo, status);
        return new ResponseEntity<>(contactModified, HttpStatus.OK);

    }
}
