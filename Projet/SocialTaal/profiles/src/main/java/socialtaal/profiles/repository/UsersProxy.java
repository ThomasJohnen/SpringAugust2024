package socialtaal.profiles.repository;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import socialtaal.profiles.models.Profile;
import socialtaal.profiles.models.User;

@Repository
@FeignClient(name = "Users")
public interface UsersProxy {

    @GetMapping("/users/{pseudo}")
    ResponseEntity<Profile> createProfile(@PathVariable String pseudo, @RequestBody Profile profile);
}
