package socialtaal.users;


import org.springframework.stereotype.Service;
import socialtaal.users.models.User;
import socialtaal.users.repository.UsersRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UsersServices {
    private final UsersRepository repository;

    public UsersServices(UsersRepository aNewrepository) {
        this.repository = aNewrepository;
    }


    public List<User> getAllUsers(){
        Iterable<User> allUsers = repository.findAll();
        return StreamSupport.stream(allUsers.spliterator(), false).toList();
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
        repository.save(userToDelete);
        return true;
    }




}
