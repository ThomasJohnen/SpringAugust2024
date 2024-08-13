package socialtaal.gateway;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialtaal.gateway.exceptions.BadRequestException;
import socialtaal.gateway.exceptions.ConflictException;
import socialtaal.gateway.exceptions.NotFoundException;
import socialtaal.gateway.exceptions.UnauthorizedException;
import socialtaal.gateway.models.*;

import java.util.List;
import java.util.Objects;

@RestController
public class GatewayController {

    private final GatewayService service;

    public GatewayController(GatewayService gatewayService) {
        this.service = gatewayService;
    }



    /* -------------------------- AUTHENTICATE ------------------------------------ */


    /**
     * Connect an investor with his credentials
     * @param credentials the credentials of the investor
     * @return a token
     *  - 200 OK if the credentials are correct
     *  - 400 BAD REQUEST if the credentials are not correct
     *  - 401 UNAUTHORIZED if the credentials are not correct
     */
    @PostMapping("/authentication/connect")
    public ResponseEntity<String> connect(@RequestBody Credentials credentials) {
        try {
            String token = service.connect(credentials);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Verify a token and return the pseudo
     * @param token the token to verify
     * @return the pseudo
     * - HttpStatus.UNAUTHORIZED if the token is not valid
     * - HttpStatus.OK if the token is valid
     */
    @PostMapping("/authentication/verify")
    public ResponseEntity<String> verify(@RequestBody String token) {
        String pseudo = service.verify(token);

        if (pseudo == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(pseudo, HttpStatus.OK);
    }

    @PutMapping("/authentication/{pseudo}")
    public ResponseEntity<Void> updateOne(@PathVariable String pseudo, @RequestBody Credentials credentials) throws NotFoundException, BadRequestException {
        if (!Objects.equals(credentials.getPseudo(), pseudo)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            service.updateCredentials(pseudo, credentials);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /* -----------------------------USERS--------------------------------- */

    /**
     * Get a user by pseudo
     * @param pseudo the pseudo of the user
     * @return the user if it exists, else return a 404 status
     */
    @GetMapping("/users/{pseudo}")
    public ResponseEntity<User> getUser(@PathVariable String pseudo) {
        try{
            User user = service.getUser(pseudo).getBody();
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get all users
     * @return a list of all users
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        try{
            List<User> users = service.getUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create a user
     * @param userReceived the user to create
     * @return the created user if it doesn't exist, else return a 409 status if the user already exists
     */
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody UserReceive userReceived) {
        if(userReceived.getPseudo() == null || userReceived.getGender() == null || userReceived.getBirthdate() == null || userReceived.getBirthCountry() == null || userReceived.getMotherTongue() == null || userReceived.getPassword() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        try {
            User savedUser = service.createUser(userReceived);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (ConflictException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * Delete a user
     * @param pseudo the pseudo of the user to delete
     * @return a 200 status if the user is deleted, else return a 404 status
     */
    @DeleteMapping("/users/{pseudo}")
    public ResponseEntity<User> deleteUser(@PathVariable String pseudo) {
        try {
            User deletedUser = service.deleteUser(pseudo);
            return new ResponseEntity<>(deletedUser, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /* -----------------------------CONTACTS--------------------------------- */

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
        try {
            Contact savedContact = service.addContact(contactRequest);
            return new ResponseEntity<>(savedContact, HttpStatus.CREATED);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
        try {
            Contact contact = service.getContact(senderPseudo, receiverPseudo);
            return new ResponseEntity<>(contact, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }


    /**
     * Get all contacts
     * @param senderPseudo the pseudo of the sender
     * @param stateContact the state of the contact
     * @return a list of all contacts
     */
    @GetMapping("/getList/{senderPseudo}/{stateContact}")
    public ResponseEntity<List<Contact>> getContacts(@PathVariable String senderPseudo, @PathVariable String stateContact) {
        if(senderPseudo == null || stateContact == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            List<Contact> contacts = service.getContacts(senderPseudo, stateContact);
            return new ResponseEntity<>(contacts, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
        if(senderPseudo == null || receiverPseudo == null || status == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Contact contactModified = service.updateContact(senderPseudo, receiverPseudo, status);
            return new ResponseEntity<>(contactModified, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    /* -----------------------------MESSAGES--------------------------------- */

    /**
     * Add a message to the database
     * @param message the message to add
     * @return the added message
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> addMessage(@RequestBody MessagePosted message) {
        System.out.println("Je suis dans le controller du gateway");
        if(message == null || message.getMessage() == null || message.getSenderPseudo() == null || message.getReceiverPseudo() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Message messageAdded = service.addMessage(message);
            return new ResponseEntity<>(messageAdded, HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get all messages for a given pseudo
     * @param pseudo
     * @return the list of messages
     */
    @GetMapping("/messages/{pseudo}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String pseudo) {
        List<Message> messages = service.getMessages(pseudo);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }


    /* -----------------------------SEARCH--------------------------------- */

    /**
     * Search users
     * @param pseudo
     * @param gender
     * @param birthCountry
     * @param motherTongue
     * @param minAge
     * @param maxAge
     * @return  a List of users that match the search criteria
     */
    @GetMapping("/search/{pseudo}")
    public ResponseEntity<List<User>> searchUsers(
            @PathVariable String pseudo,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String birthCountry,
            @RequestParam(required = false) String motherTongue,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge
    ) {
        if(pseudo == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("La requête controller est bien trouvée");

        return new ResponseEntity<>(service.searchUsers(pseudo, gender, minAge, maxAge, birthCountry, motherTongue), HttpStatus.OK);
    }

}
