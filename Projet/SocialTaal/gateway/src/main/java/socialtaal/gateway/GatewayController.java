package socialtaal.gateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialtaal.gateway.exceptions.BadRequestException;
import socialtaal.gateway.exceptions.UnauthorizedException;
import socialtaal.gateway.models.Credentials;

@RestController
public class GatewayController {

    private final GatewayService service;

    public GatewayController(GatewayService gatewayService) {
        this.service = gatewayService;
    }

    @PostMapping("/authentication/connect")
    public ResponseEntity<String> connect(@RequestBody Credentials credentials) {
        try {
            String token = service.connect(credentials);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/authentication/{username}")
    public ResponseEntity<Void> updateCredentials(@PathVariable String username, @RequestBody Credentials credentials) {
        if (!username.equals(credentials.getUsername())) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            service.updateCredentials(username, credentials);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
