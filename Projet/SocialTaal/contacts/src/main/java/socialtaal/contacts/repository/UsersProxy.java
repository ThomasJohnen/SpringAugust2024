package socialtaal.contacts.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import socialtaal.contacts.models.User;

@Repository
@FeignClient(name = "users")
public interface UsersProxy {

    @GetMapping("/users/{pseudo}")
    public ResponseEntity<User> getUser(@PathVariable String pseudo);
}
