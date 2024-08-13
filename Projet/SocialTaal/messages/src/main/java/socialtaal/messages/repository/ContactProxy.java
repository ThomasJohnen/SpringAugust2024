package socialtaal.messages.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import socialtaal.messages.models.Contact;


@Repository
@FeignClient(name = "Contacts")
public interface ContactProxy {
    @GetMapping("/{senderPseudo}/{receiverPseudo}")
    ResponseEntity<Contact> getContact(@PathVariable String senderPseudo, @PathVariable String receiverPseudo);
}
