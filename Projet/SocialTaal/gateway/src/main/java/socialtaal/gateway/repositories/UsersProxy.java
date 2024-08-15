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
    ResponseEntity<List<User>> getUsers();

    @PostMapping("/users")
    ResponseEntity<User> createUser( @RequestBody UserReceive userReceived);

    @DeleteMapping("/users/{pseudo}")
    ResponseEntity<User> deleteUser(@PathVariable String pseudo);

    @PutMapping("/users/{pseudo}")
    ResponseEntity<User> updateUser(@PathVariable String pseudo, @RequestBody socialtaal.gateway.models.Profile profileToUpdate);
}
