package socialtaal.gateway;

import org.springframework.http.ResponseEntity;
import socialtaal.gateway.models.*;
import socialtaal.gateway.exceptions.BadRequestException;
import socialtaal.gateway.exceptions.ConflictException;
import socialtaal.gateway.exceptions.NotFoundException;
import socialtaal.gateway.exceptions.UnauthorizedException;
import feign.FeignException;
import org.springframework.stereotype.Service;
import socialtaal.gateway.repositories.*;

import java.util.List;

@Service
public class GatewayService {

    private final AuthenticationProxy authenticationProxy;
    private final UsersProxy userProxy;
    private final SearchProxy searchProxy;
    private final ContactProxy contactProxy;
    private final MessageProxy messageProxy;

    public GatewayService(AuthenticationProxy authenticationProxy, SearchProxy searchProxy, ContactProxy contactProxy, UsersProxy userProxy, MessageProxy messageProxy) {
        this.userProxy = userProxy;
        this.authenticationProxy = authenticationProxy;
        this.messageProxy = messageProxy;
        this.contactProxy = contactProxy;
        this.searchProxy = searchProxy;

    }

    /* -------------------------- AUTHENTICATE ------------------------------------ */
    public String connect(Credentials credentials) throws BadRequestException, UnauthorizedException {
        try {
            return authenticationProxy.connect(credentials);
        } catch (FeignException e) {
            if (e.status() == 400) throw new BadRequestException();
            else if (e.status() == 401) throw new UnauthorizedException();
            else throw e;
        }
    }

    public String verify(String token) {
        try {
            return authenticationProxy.verify(token);
        } catch (FeignException e) {
            if (e.status() == 401) return null;
            else throw e;
        }
    }

    /**
     * Update the credentials of the user
     *
     * @param pseudo      the pseudo of the user
     * @param credentials the new credentials
     * @throws NotFoundException   if the user is not found
     * @throws BadRequestException if the credentials are not valid
     */
    public void updateCredentials(String pseudo, Credentials credentials) throws NotFoundException, BadRequestException {
        try {
            authenticationProxy.updateCredentials(pseudo, credentials);
        } catch (FeignException e) {
            if (e.status() == 400) throw new BadRequestException();
            else if (e.status() == 404) throw new NotFoundException();
            else throw e;
        }
    }

    /* -----------------------------USERS--------------------------------- */

    public ResponseEntity<User> getUser(String pseudo) throws NotFoundException {
        try{
            return userProxy.getUser(pseudo);
        }
        catch (FeignException e) {
            throw new NotFoundException();
        }
    }

    public List<User> getUsers() {
        try{return userProxy.getUsers().getBody();}
        catch (FeignException e) {
            throw e;
        }

    }

    public User createUser(UserReceive userReceived) throws BadRequestException, ConflictException {
        try {
            return userProxy.createUser(userReceived).getBody();
        } catch (FeignException e) {
            if (e.status() == 400) throw new BadRequestException();
            else if(e.status() == 409) throw new ConflictException();
            else throw e;
        }
    }

    public User deleteUser(String pseudo) throws NotFoundException {
        try {
            return userProxy.deleteUser(pseudo).getBody();
        } catch (FeignException e) {
            if (e.status() == 404) throw new NotFoundException();
            else throw e;
        }
    }

    public User updateUser(String pseudo, Profile profile) throws NotFoundException, BadRequestException {
        try {
            return userProxy.updateUser(pseudo, profile).getBody();
        } catch (FeignException e) {
            if (e.status() == 404) throw new NotFoundException();
            else if(e.status() == 400) throw new BadRequestException();
            else throw e;
        }
    }



    /* -----------------------------CONTACTS--------------------------------- */

    public Contact addContact(ContactRequest contactRequest) throws BadRequestException, ConflictException, UnauthorizedException {
        try {
            return contactProxy.addContact(contactRequest).getBody();
        } catch (FeignException e) {
            if (e.status() == 400) throw new BadRequestException();
            else if(e.status() == 409) throw new ConflictException();
            else if(e.status() == 401) throw new UnauthorizedException();
            else throw e;
        }
    }

    public Contact getContact(String senderPseudo, String receiverPseudo) throws NotFoundException, BadRequestException {
        try {
            return contactProxy.getContact(senderPseudo, receiverPseudo).getBody();
        } catch (FeignException e) {
            if (e.status() == 404) throw new NotFoundException();
            else if(e.status() == 400) throw new BadRequestException();
            else throw e;
        }
    }

    public List<Contact> getContacts(String senderPseudo, String stateContact) throws NotFoundException, BadRequestException {
        try {
            return contactProxy.getContacts(senderPseudo, stateContact).getBody();
        } catch (FeignException e) {
            if (e.status() == 404) throw new NotFoundException();
            else if (e.status() == 400) throw new BadRequestException();
            else throw e;
        }
    }

    public Contact updateContact(String senderPseudo, String receiverPseudo, String status) throws NotFoundException, BadRequestException {
        System.out.println("On est dans la suite de l'appel, dans la partie service");
        try {
            System.out.println("on tente d'appeler le proxy");
            return contactProxy.modifyContact(senderPseudo, receiverPseudo, status).getBody();
        } catch (FeignException e) {
            if (e.status() == 404) throw new NotFoundException();
            else if(e.status() == 400) throw new BadRequestException();
            else throw e;
        }
    }



    /* -----------------------------MESSAGES--------------------------------- */


    public Message addMessage(MessagePosted messageEchange) throws BadRequestException, NotFoundException, UnauthorizedException {
        try {
            return messageProxy.addMessage(messageEchange).getBody();
        } catch (FeignException e) {
            if (e.status() == 400) throw new BadRequestException();
            else if (e.status() == 404) throw new NotFoundException();
            else if (e.status() == 401) throw new UnauthorizedException();
            else throw e;
        }
    }

    public List<Message> getMessages(String pseudo) throws NotFoundException, BadRequestException {
        try{
            return messageProxy.getMessages(pseudo).getBody();
        }
        catch (FeignException e) {
            if(e.status() == 404) throw new NotFoundException();
            if(e.status() == 400) throw new BadRequestException();
            else throw e;
        }

    }

    /* -------------------------------SEARCH------------------------------- */

    public List<User> searchUsers(String pseudo, String gender, Integer minAge, Integer maxAge, String birthCountry, String motherTongue) throws NotFoundException, BadRequestException {
        try {
            return searchProxy.searchUsers(pseudo, gender, birthCountry, motherTongue, minAge, maxAge).getBody();
        } catch (FeignException e) {
            if(e.status() == 404) throw new NotFoundException();
            else if(e.status() == 400) throw new BadRequestException();
            throw e;
        }

    }
}
