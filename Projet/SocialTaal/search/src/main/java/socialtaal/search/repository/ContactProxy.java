package socialtaal.search.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import socialtaal.search.models.Contact;

import java.util.List;

@Repository
@FeignClient(name = "Contacts")
public interface ContactProxy {


    @GetMapping("/getList/{senderPseudo}")
    ResponseEntity<List<Contact>> getContacts(@PathVariable String senderPseudo);
}
