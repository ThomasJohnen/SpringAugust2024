package socialtaal.profiles.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import socialtaal.profiles.models.Profile;

@Repository
public interface ProfilesRepository extends CrudRepository<Profile, String>{
}
