package socialtaal.gateway.repositories;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import socialtaal.gateway.models.User;
import socialtaal.gateway.models.UserReceive;

import java.util.List;


@Repository
@FeignClient(name = "users")
public interface UsersProxy {

    @GetMapping("/users/{pseudo}")
    ResponseEntity<User> getUser(@PathVariable String pseudo);

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers();

    @PostMapping("/users")
    public ResponseEntity<User> createUser( @RequestBody UserReceive userReceived);

    @DeleteMapping("/users/{pseudo}")
    public ResponseEntity<User> deleteUser(@PathVariable String pseudo);
}
