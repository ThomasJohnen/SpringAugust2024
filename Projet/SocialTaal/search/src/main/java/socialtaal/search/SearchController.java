package socialtaal.search;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import socialtaal.search.models.Genders;
import socialtaal.search.models.User;

import java.util.List;

@RestController
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * Search users
     * @param pseudo
     * @param gender
     * @param birthCountry
     * @param motherTongue
     * @param minAge
     * @param maxAge
     * @return  a List of users that match the search criteria
     */
    @GetMapping("/search/{pseudo}")
    public ResponseEntity<List<User>> searchUsers(
            @PathVariable String pseudo,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String birthCountry,
            @RequestParam(required = false) String motherTongue,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge
    ) {
        if(pseudo == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(searchService.getUser(pseudo) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(gender != null) {
            boolean isGenderRight = false;
            for (Genders o : Genders.values()) {
                if(o.toString().equals(gender))
                    isGenderRight = true;
            }
            if(!isGenderRight) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        List<User> users = searchService.searchUsers(pseudo, gender, minAge, maxAge, birthCountry, motherTongue);
        if(users == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
