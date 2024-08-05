package socialtaal.gateway.repositories;


import socialtaal.gateway.models.Credentials;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name = "authentication")
public interface AuthenticationProxy {

    @PostMapping("/authentication/verify")
    String verify(@RequestBody String token);

    @PostMapping("/authentication/connect")
    String connect(@RequestBody Credentials credentials);

    @PutMapping("/authentication/{username}")
    void updateCredentials(@PathVariable String username, @RequestBody Credentials credentials);
}
