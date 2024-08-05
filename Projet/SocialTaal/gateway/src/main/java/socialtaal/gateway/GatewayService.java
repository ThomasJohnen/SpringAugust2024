package socialtaal.gateway;

import socialtaal.gateway.exceptions.BadRequestException;
import socialtaal.gateway.exceptions.NotFoundException;
import socialtaal.gateway.exceptions.UnauthorizedException;
import socialtaal.gateway.models.Credentials;
import feign.FeignException;
import org.springframework.stereotype.Service;
import socialtaal.gateway.repositories.AuthenticationProxy;

@Service
public class GatewayService {

    private final AuthenticationProxy authenticationProxy;

    public GatewayService(AuthenticationProxy authenticationProxy) {
        this.authenticationProxy = authenticationProxy;
    }

    public String connect(Credentials credentials) throws BadRequestException, UnauthorizedException {
        try {
            return authenticationProxy.connect(credentials);
        } catch (FeignException e) {
            if (e.status() == 400) throw new BadRequestException();
            else if (e.status() == 401) throw new UnauthorizedException();
            else throw e;
        }
    }

    public String verify(String token) {
        try {
            return authenticationProxy.verify(token);
        } catch (FeignException e) {
            if (e.status() == 401) return null;
            else throw e;
        }
    }

    public void updateCredentials(String username, Credentials credentials) throws NotFoundException, BadRequestException {
        User user;
        try {
            user = UserProxy.getInvestor(username);
        } catch (FeignException e) {
            if (e.status() == 404) throw new NotFoundException();
            else throw e;
        }

}
