package socialtaal.users.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import socialtaal.users.models.Profile;

@Repository
@FeignClient(name = "Profiles")
public interface ProfileProxy {

    @PostMapping("/profiles/{pseudo}")
    ResponseEntity<Profile> createProfile(@PathVariable String pseudo, @RequestBody Profile profile);

    @DeleteMapping("/profile/{pseudo}")
    ResponseEntity<Void> deleteProfile(@PathVariable String pseudo);
}
