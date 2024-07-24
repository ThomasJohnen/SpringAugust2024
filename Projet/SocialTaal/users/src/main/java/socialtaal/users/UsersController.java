package socialtaal.users;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialtaal.users.models.User;

@RestController
public class UsersController {

    private final UsersServices service;

    public UsersController(UsersServices service){
        this.service = service;
    }

    @GetMapping("/users/{pseudo}")
    public ResponseEntity<User> getUser(@PathVariable String pseudo){
        User user = service.getUser(pseudo);
        if(user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users/{pseudo}")
    public ResponseEntity<User> createUser(@PathVariable String pseudo, @RequestBody User user){
        if(user.getPseudo() == null || user.getGender() == null || user.getBirthdate() == null || user.getBirthCountry() == null || user.getMotherTongue() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(service.getUser(pseudo) != null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        User savedUser = service.createOne(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{pseudo}")
    public ResponseEntity<User> deleteUser(@PathVariable String pseudo){
        if(service.getUser(pseudo) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        service.deleteUser(pseudo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
