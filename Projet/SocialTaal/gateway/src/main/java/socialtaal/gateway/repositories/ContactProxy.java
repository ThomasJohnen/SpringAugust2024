package socialtaal.gateway.repositories;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import socialtaal.gateway.models.Contact;
import socialtaal.gateway.models.ContactRequest;


import java.util.List;

@Repository
@FeignClient(name = "Contacts")
public interface ContactProxy {


    @GetMapping("/getList/{senderPseudo}/{stateContact}")
    ResponseEntity<List<Contact>> getContacts(@PathVariable String senderPseudo, @PathVariable String stateContact);

    @PostMapping("/contact")
    ResponseEntity<Contact> addContact(@RequestBody ContactRequest contactRequest);

    @GetMapping("/{senderPseudo}/{receiverPseudo}")
    ResponseEntity<Contact> getContact(@PathVariable String senderPseudo, @PathVariable String receiverPseudo);

    @PutMapping("/{senderPseudo}/{receiverPseudo}/{status}")
    ResponseEntity<Contact> modifyContact(@PathVariable String senderPseudo, @PathVariable String receiverPseudo, @PathVariable String status);

}

