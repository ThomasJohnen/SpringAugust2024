package socialtaal.users;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialtaal.users.models.Genders;
import socialtaal.users.models.Profile;
import socialtaal.users.models.User;
import socialtaal.users.models.UserReceive;

import java.util.List;

@RestController
public class UsersController {

    private final UsersServices service;


    public UsersController(UsersServices service) {
        this.service = service;
    }

    /**
     * Get all users
     * @return a list of all users
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users= service.getAllUsers();
        if(users.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Get a user by pseudo
     * @param pseudo the pseudo of the user
     * @return the user if it exists, else return a 404 status
     */
    @GetMapping("/users/{pseudo}")
    public ResponseEntity<User> getUser(@PathVariable String pseudo){
        User user = service.getUser(pseudo);
        if(user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Create a user
     * @param userReceived the user to create
     * @return the created user if it doesn't exist, else return a 409 status
     */
    @PostMapping("/users")
    public ResponseEntity<User> createUser( @RequestBody UserReceive userReceived){
        if(userReceived.getPseudo() == null || userReceived.getGender() == null || userReceived.getBirthdate() == null || userReceived.getBirthCountry() == null || userReceived.getMotherTongue() == null || userReceived.getPassword() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        boolean isGenderRight = false;
        for (Object o : Genders.values()) {
            if(o.equals(userReceived.getGender()))
                isGenderRight = true;
        }
        if(!isGenderRight)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        User savedUser = service.createOne(userReceived);
        if(savedUser == null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    /**
     * Delete a user
     * @param pseudo the pseudo of the user to delete
     * @return a 200 status if the user is deleted, else return a 404 status
     */
    @DeleteMapping("/users/{pseudo}")
    public ResponseEntity<User> deleteUser(@PathVariable String pseudo){
        if(service.getUser(pseudo) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        service.deleteUser(pseudo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Update a user
     * @param pseudo the pseudo of the user to update
     * @param profileToUpdate the profile to update
     * @return the updated user if it exists, else return a 404 status
     */
    @PutMapping("/users/{pseudo}")
    public ResponseEntity<User> updateUser(@PathVariable String pseudo, @RequestBody Profile profileToUpdate){
       if(pseudo == null || profileToUpdate.getBiography() == null)
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       User user = service.modifyUser(pseudo, profileToUpdate);
       if(user == null)
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
