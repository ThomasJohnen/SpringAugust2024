package socialtaal.authentication;

import socialtaal.authentication.models.UnsafeCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    /**
     * Connect a user and return a token
     * @param credentials the credentials to connect
     * @return the token
     * - HttpStatus.BAD_REQUEST if the credentials are not valid
     * - HttpStatus.UNAUTHORIZED if the credentials are not valid
     * - HttpStatus.OK if the credentials are valid
     */
    @PostMapping("/authentication/connect")
    public ResponseEntity<String> connect(@RequestBody UnsafeCredentials credentials) {
        if (credentials.invalid()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        String token =  service.connect(credentials);

        if (token == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    /**
     * Verify a token and return the pseudopseudo
     * @param token the token to verify
     * @return the pseudo
     * - HttpStatus.UNAUTHORIZED if the token is not valid
     * - HttpStatus.OK if the token is valid
     */
    @PostMapping("/authentication/verify")
    public ResponseEntity<String> verify(@RequestBody String token) {
        String pseudo = service.verify(token);

        if (pseudo == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(pseudo, HttpStatus.OK);
    }

    /**
     * Create a new user
     * @param pseudo the pseudo of the user to create
     * @param credentials the credentials of the user to create
     * @return void
     * - HttpStatus.BAD_REQUEST if the credentials are not valid
     * - HttpStatus.CONFLICT if the user already exists
     * - HttpStatus.CREATED if the user is successfully created
     */
    @PostMapping("/authentication/{pseudo}")
    public ResponseEntity<Void> createOne(@PathVariable String pseudo, @RequestBody UnsafeCredentials credentials) {
        if (!credentials.getPseudo().equals(pseudo)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (credentials.invalid()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        boolean created = service.createOne(credentials);

        if (!created) return new ResponseEntity<>(HttpStatus.CONFLICT);
        else return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Update a user
     * @param pseudo the pseudo of the user to update
     * @param credentials the credentials of the user to update
     * @return void
     * - HttpStatus.BAD_REQUEST if the credentials are not valid
     * - HttpStatus.NOT_FOUND if the user is not found
     * - HttpStatus.OK if the user is successfully updated
     */
    @PutMapping("/authentication/{pseudo}")
    public ResponseEntity<Void> updateOne(@PathVariable String pseudo, @RequestBody UnsafeCredentials credentials) {
        System.out.println("On arrive au bon endroit");
        if (!Objects.equals(pseudo, credentials.getPseudo())) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (credentials.invalid()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        boolean found = service.updateOne(credentials);

        if (!found) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Delete a user
     * @param pseudo the username of the user to delete
     * @return void
     * - HttpStatus.NOT_FOUND if the user is not found
     * - HttpStatus.OK if the user is successfully deleted
     */
    @DeleteMapping("/authentication/{pseudo}")
    public ResponseEntity<Void> deleteCredentials(@PathVariable String pseudo) {
        boolean found = service.deleteOne(pseudo);

        if (!found) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(HttpStatus.OK);
    }
}
