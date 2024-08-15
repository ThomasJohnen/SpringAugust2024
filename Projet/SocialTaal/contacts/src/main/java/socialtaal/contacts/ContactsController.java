package socialtaal.contacts;


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


    public ContactsController(ContactsService aNewservice) {
        this.service = aNewservice;

    }

    /**
     * Add a contact
     * @param contactRequest the contact to add with the sender and receiver pseudo
     * @return the created contact if it doesn't exist, else return a 400 status
     */
    @PostMapping("/contact")
    public ResponseEntity<Contact> addContact(@RequestBody ContactRequest contactRequest) {
        if(contactRequest.getSenderPseudo().equals(contactRequest.getReceiverPseudo())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(service.getUser(contactRequest.getReceiverPseudo()) == null || service.getUser(contactRequest.getSenderPseudo()) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(!service.getUser(contactRequest.getReceiverPseudo()).isContactable() || !service.getUser(contactRequest.getSenderPseudo()).isContactable()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if(getContact(contactRequest.getSenderPseudo(), contactRequest.getReceiverPseudo()).getStatusCodeValue() == 200){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Contact savedContact = service.save(contactRequest);
        if (savedContact == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedContact, HttpStatus.CREATED);
    }

    /**
     * Get a contact by sender and receiver pseudo
     * @param senderPseudo the pseudo of the sender
     * @param receiverPseudo the pseudo of the receiver
     * @return the contact if it exists, else return a 404 status
     */
    @GetMapping("/{senderPseudo}/{receiverPseudo}")
    public ResponseEntity<Contact> getContact(@PathVariable String senderPseudo, @PathVariable String receiverPseudo) {
        if(senderPseudo == null || receiverPseudo == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(service.getUser(receiverPseudo) == null || service.getUser(senderPseudo) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Contact contact = service.getContact(senderPseudo, receiverPseudo);
        if (contact == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    /**
     * Get all contacts
     * @param senderPseudo the pseudo of the sender
     * @param stateContact the state of the contact
     * @return a list of all contacts
     */
    @GetMapping("/getList/{senderPseudo}/{stateContact}")
    public ResponseEntity<List<Contact>> getContacts(@PathVariable String senderPseudo, @PathVariable String stateContact) {
        if(stateContact == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(service.getUser(senderPseudo) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Contact> contacts = service.getContacts(senderPseudo, stateContact);
        if (contacts == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @GetMapping("/getList/{senderPseudo}")
    public ResponseEntity<List<Contact>> getContacts(@PathVariable String senderPseudo) {
        if(senderPseudo == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(service.getUser(senderPseudo) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Contact> contacts = service.getAllContactsForAUSer(senderPseudo);
        if (contacts == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    /**
     * Modify a contact
     * @param senderPseudo the pseudo of the sender
     * @param receiverPseudo the pseudo of the receiver
     * @param status the state of the contact
     * @return the modified contact if it exists, else return a 404 status
     */
    @PutMapping("/{senderPseudo}/{receiverPseudo}/{status}")
    public ResponseEntity<Contact> modifyContact(@PathVariable String senderPseudo, @PathVariable String receiverPseudo, @PathVariable String status) {
        if(status == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(service.getUser(receiverPseudo) == null || service.getUser(senderPseudo) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Contact contactModified = service.updateContact(senderPseudo, receiverPseudo, status);
        if (contactModified == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(contactModified, HttpStatus.OK);

    }

}
