package socialtaal.profiles;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import socialtaal.profiles.models.Profile;

@RestController
public class profilesController {

    private final ProfilesServices service;

    public profilesController(ProfilesServices service) {
        this.service = service;
    }

    @GetMapping("/profiles/{username}")
    public ResponseEntity<Profile> getProfile(@PathVariable String username){
        Profile profile = service.getProfile(username);
        if(profile == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else return new ResponseEntity<>(profile, HttpStatus.OK);
    }

}
