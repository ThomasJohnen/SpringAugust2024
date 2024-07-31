package socialtaal.profiles;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialtaal.profiles.models.Profile;

import java.util.List;

@RestController
public class profilesController {

    private final ProfilesServices service;

    public profilesController(ProfilesServices service) {
        this.service = service;
    }

    @GetMapping("/profile/{pseudo}")
    public ResponseEntity<Profile> getProfile(@PathVariable String pseudo){
        Profile profile = service.getProfile(pseudo);
        if(profile == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<List<Profile>> getAllProfiles(){
        List<Profile> profiles = service.getAllProfiles();
        if(profiles == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }


    @PostMapping("/profile/{pseudo}")
    public ResponseEntity<Profile> createProfile(@PathVariable String pseudo, @RequestBody Profile profile){
        if(pseudo == null || profile == null || !pseudo.equals(profile.getPseudo())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(service.getProfile(pseudo) != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Profile savedProfile = service.createProfile(profile);
        return new ResponseEntity<>(savedProfile, HttpStatus.CREATED);
    }

    @PatchMapping("/profile/{pseudo}")
    public ResponseEntity<Profile> updateProfile(@PathVariable String pseudo, @RequestBody Profile profile){
        if(pseudo == null || profile == null || !pseudo.equals(profile.getPseudo())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(service.getProfile(pseudo) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Profile savedProfile = service.updateProfile(profile);
        return new ResponseEntity<>(savedProfile, HttpStatus.OK);
    }

    @DeleteMapping("/profile/{pseudo}")
    public ResponseEntity<Void> deleteProfile(@PathVariable String pseudo){
        if(pseudo == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(service.getProfile(pseudo) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.deleteProfile(pseudo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
