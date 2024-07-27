package socialtaal.messages.repository;

import org.apache.hc.core5.http.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, String> {

}
