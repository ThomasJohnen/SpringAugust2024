package socialtaal.users;


import org.springframework.stereotype.Service;
import socialtaal.users.models.Profile;
import socialtaal.users.models.UnsafeCredentials;
import socialtaal.users.models.User;
import socialtaal.users.models.UserReceive;
import socialtaal.users.repository.AuthenticateProxy;
import socialtaal.users.repository.UsersRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UsersServices {
    private final UsersRepository repository;
    private final AuthenticateProxy authenticateProxy;

    public UsersServices(UsersRepository aNewrepository, AuthenticateProxy authenticateProxy) {
        this.repository = aNewrepository;
        this.authenticateProxy = authenticateProxy;
    }


    /**
     * Get all users
     * @return a list of all users
     */
    public List<User> getAllUsers(){
        Iterable<User> allUsers = repository.findAll();
        return StreamSupport.stream(allUsers.spliterator(), false).toList();
    }

    /**
     * Get a user by pseudo
     * @param pseudo the pseudo of the user
     * @return the user if it exists, else return null
     */
    public User getUser(String pseudo){
        return repository.findById(pseudo).orElse(null);
    }

    /**
     * Create a user
     * @param user the user to create
     * @return the created user if it doesn't exist, else return null
     */
    public User createOne(UserReceive user){
        if(repository.existsById(user.getPseudo())) return null;
        User newUser = new User();
        newUser.setPseudo(user.getPseudo());
        newUser.setGender(user.getGender());
        newUser.setBirthdate(user.getBirthdate());
        newUser.setBirthCountry(user.getBirthCountry());
        newUser.setMotherTongue(user.getMotherTongue());
        if(user.getBiography() != null)
            newUser.setBiography(user.getBiography());
        if(user.isContactable())
            newUser.setContactable(true);
        UnsafeCredentials credentials = new UnsafeCredentials();
        credentials.setPseudo(user.getPseudo());
        credentials.setPassword(user.getPassword());
        authenticateProxy.createOne(user.getPseudo(), credentials);
        return repository.save(newUser);
    }

    /**
     * disable a user
     * @param pseudo the pseudo of the user to disable
     * @return true if the user is disabled, else return false
     */
    public boolean deleteUser(String pseudo){
        User userToDelete = repository.findById(pseudo).orElse(null);
        if(userToDelete == null) return false;
        userToDelete.setDisable(true);
        userToDelete.setContactable(false);
        repository.save(userToDelete);
        return true;
    }


    /**
     * modify a user
     * @param pseudo the pseudo of the user to modify
     * @param profileToUpdate the profile to update
     * @return the modified user if it exists, else return null
     */
    public User modifyUser(String pseudo, Profile profileToUpdate){
        User userToModify = repository.findById(pseudo).orElse(null);
        if(userToModify == null) return null;
        userToModify.setContactable(profileToUpdate.isContactable());
        userToModify.setBiography(profileToUpdate.getBiography());
        return repository.save(userToModify);

    }




}
