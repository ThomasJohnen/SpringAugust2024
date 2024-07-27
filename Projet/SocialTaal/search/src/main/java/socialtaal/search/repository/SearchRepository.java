package socialtaal.search.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import socialtaal.search.models.UserForSearch;

@Repository
public interface SearchRepository extends CrudRepository<UserForSearch, String> {
}
