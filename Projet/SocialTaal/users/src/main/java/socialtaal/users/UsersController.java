package socialtaal.users;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialtaal.users.models.Profile;
import socialtaal.users.models.User;
import socialtaal.users.models.UserReceive;
import socialtaal.users.repository.ProfileProxy;

@RestController
public class UsersController {

    private final UsersServices service;
    private final ProfileProxy profileProxy;

    public UsersController(UsersServices service, ProfileProxy profileProxy) {
        this.service = service;
        this.profileProxy = profileProxy;
    }

    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> getUsers(){
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{pseudo}")
    public ResponseEntity<User> getUser(@PathVariable String pseudo){
        User user = service.getUser(pseudo);
        if(user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users/{pseudo}")
    public ResponseEntity<User> createUser(@PathVariable String pseudo, @RequestBody UserReceive userReceived){
        if(userReceived.getPseudo() == null || userReceived.getGender() == null || userReceived.getBirthdate() == null || userReceived.getBirthCountry() == null || userReceived.getMotherTongue() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(service.getUser(pseudo) != null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        User user = new User();
        user.setPseudo(pseudo);
        user.setGender(userReceived.getGender());
        user.setBirthdate(userReceived.getBirthdate());
        user.setBirthCountry(userReceived.getBirthCountry());
        user.setMotherTongue(userReceived.getMotherTongue());
        if(userReceived.isDisable() == true)
            user.setDisable(true);
        User savedUser = service.createOne(user);
        Profile profile = new Profile();
        profile.setPseudo(pseudo);
        profile.setBiography(userReceived.getBiography());
        if(userReceived.isContactable() == true)
            profile.setContactable(true);
        profileProxy.createProfile(profile.getPseudo(), profile);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{pseudo}")
    public ResponseEntity<User> deleteUser(@PathVariable String pseudo){
        if(service.getUser(pseudo) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        service.deleteUser(pseudo);
        profileProxy.deleteProfile(pseudo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
