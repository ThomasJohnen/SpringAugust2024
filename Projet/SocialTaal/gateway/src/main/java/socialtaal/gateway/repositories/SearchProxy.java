package socialtaal.gateway.repositories;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import socialtaal.gateway.models.User;

import java.util.List;

@Repository
@FeignClient(name = "search")
public interface SearchProxy {

    @GetMapping("/search/{pseudo}")
    ResponseEntity<List<User>> searchUsers(
            @PathVariable String pseudo,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String birthCountry,
            @RequestParam(required = false) String motherTongue,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge
    );
}
