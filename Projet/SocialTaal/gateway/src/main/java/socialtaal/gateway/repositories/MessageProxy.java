package socialtaal.gateway.repositories;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import socialtaal.gateway.models.Message;
import socialtaal.gateway.models.MessagePosted;

import java.util.List;

@Repository
@FeignClient(name = "messages")
public interface MessageProxy {

    @PostMapping("/messages")
    ResponseEntity<Message> addMessage(@RequestBody MessagePosted message);

    @GetMapping("/messages/{pseudo}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String pseudo);
}
