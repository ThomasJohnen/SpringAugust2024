package socialtaal.users;


import org.springframework.stereotype.Service;
import socialtaal.users.models.User;
import socialtaal.users.repository.UsersRepository;

@Service
public class UsersServices {
    private final UsersRepository repository;

    public UsersServices(UsersRepository aNewrepository) {
        this.repository = aNewrepository;
    }

    public User getUser(String pseudo){
        return repository.findById(pseudo).orElse(null);
    }

    public User createOne(User user){
        if(repository.existsById(user.getPseudo())) return null;
        return repository.save(user);
    }


    public boolean deleteUser(String pseudo){
        User userToDelete = repository.findById(pseudo).orElse(null);
        if(userToDelete == null) return false;
        userToDelete.setDisable(true);
        return true;
    }




}
