package socialtaal.authentication;

import socialtaal.authentication.models.SafeCredentials;
import socialtaal.authentication.models.UnsafeCredentials;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationRepository repository;
    private final Algorithm jwtAlgorithm;
    private final JWTVerifier jwtVerifier;

    public AuthenticationService(AuthenticationRepository repository, AuthenticationProperties properties) {
        this.repository = repository;
        this.jwtAlgorithm = Algorithm.HMAC512(properties.getSecret());
        this.jwtVerifier = JWT.require(this.jwtAlgorithm).withIssuer("auth0").build();
    }


    /**
     * Connects user with credentials
     * @param unsafeCredentials The credentials with insecure password
     * @return The JWT token, or null if the user couldn't be connected
     */
    public String connect(UnsafeCredentials unsafeCredentials) {
        SafeCredentials safeCredentials = repository.findById(unsafeCredentials.getUsername()).orElse(null);
        if (safeCredentials == null) return null;
        if (!BCrypt.checkpw(unsafeCredentials.getPassword(), safeCredentials.getHashedPassword())) return null;
        return JWT.create().withIssuer("auth0").withClaim("username", safeCredentials.getUsername()).sign(jwtAlgorithm);
    }


    /**
     * Verifies JWT token
     * @param token The JWT token
     * @return The pseudo of the user, or null if the token couldn't be verified
     */
    public String verify(String token) {
        try {
            String pseudo = jwtVerifier.verify(token).getClaim("username").asString();
            if (!repository.existsById(pseudo)) return null;
            return pseudo;
        } catch (JWTVerificationException e) {
            return null;
        }
    }


    /**
     * Creates credentials in repository
     * @param unsafeCredentials The credentials with insecure password
     * @return True if the credentials were created, or false if they already exist
     */
    public boolean createOne(UnsafeCredentials unsafeCredentials) {
        if (repository.existsById(unsafeCredentials.getUsername())) return false;
        String hashedPassword = BCrypt.hashpw(unsafeCredentials.getPassword(), BCrypt.gensalt());
        repository.save(unsafeCredentials.makeSafe(hashedPassword));
        return true;
    }

    /**
     * Updates credentials in repository
     * @param unsafeCredentials The credentials with insecure password
     * @return True if the credentials were updated, or false if they couldn't be found
     */
    public boolean updateOne(UnsafeCredentials unsafeCredentials) {
        if (!repository.existsById(unsafeCredentials.getUsername())) return false;
        String hashedPassword = BCrypt.hashpw(unsafeCredentials.getPassword(), BCrypt.gensalt());
        repository.save(unsafeCredentials.makeSafe(hashedPassword));
        return true;
    }

    /**
     * Deletes credentials in repository
     * @param pseudo The pseudo of the user
     * @return True if the credentials were deleted, or false if they couldn't be found
     */
    public boolean deleteOne(String pseudo) {
        if (!repository.existsById(pseudo)) return false;
        repository.deleteById(pseudo);
        return true;
    }

}
