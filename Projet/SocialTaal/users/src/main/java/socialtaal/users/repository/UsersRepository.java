package socialtaal.users.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import socialtaal.users.models.User;


@Repository
public interface UsersRepository extends CrudRepository<User, String> {
}
