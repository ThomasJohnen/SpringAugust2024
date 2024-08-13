package socialtaal.search.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import socialtaal.search.models.User;

import java.util.List;


@Repository
@FeignClient(name = "users")
public interface UsersProxy {

    @GetMapping("/users/{pseudo}")
    ResponseEntity<User> getUser(@PathVariable String pseudo);

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers();
}
