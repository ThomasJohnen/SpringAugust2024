package socialtaal.users.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import socialtaal.users.models.UnsafeCredentials;

@Repository
@FeignClient(name = "authentication")
public interface AuthenticateProxy {

    @PostMapping("/authentication/{pseudo}")
    void createOne(@PathVariable String pseudo, @RequestBody UnsafeCredentials credentials);
}
