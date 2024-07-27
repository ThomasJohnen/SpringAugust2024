package socialtaal.search;

import org.springframework.stereotype.Service;
import socialtaal.search.repository.SearchRepository;

@Service
public class SearchService {

    private final SearchRepository repository;

    public SearchService(SearchRepository repository) {
        this.repository = repository;
    }
}
