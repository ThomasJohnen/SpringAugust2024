package socialtaal.search;

import org.springframework.stereotype.Controller;

@Controller
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }
}
