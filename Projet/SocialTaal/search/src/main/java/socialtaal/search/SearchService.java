package socialtaal.search;

import org.springframework.stereotype.Service;
import socialtaal.search.models.Genders;
import socialtaal.search.models.User;
import socialtaal.search.repository.ContactProxy;
import socialtaal.search.repository.UsersProxy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.Period;

@Service
public class SearchService {

    private final ContactProxy contactProxy;

    private final UsersProxy usersProxy;

    public SearchService(ContactProxy contactProxy, UsersProxy usersProxy) {
        this.contactProxy = contactProxy;
        this.usersProxy = usersProxy;
    }

    /**
     * Search users
     * @param pseudo
     * @param gender
     * @param minAge
     * @param maxAge
     * @param birthCountry
     * @param motherTongue
     * @return a List of users that match the search criteria
     */
    public List<User> searchUsers(String pseudo, String gender, Integer minAge, Integer maxAge, String birthCountry, String motherTongue) {


        List<User> allUsers = usersProxy.getUsers().getBody();
        if(allUsers == null) {
            return null;
        }



        List<String> existingContacts = Optional.ofNullable(contactProxy.getContacts(pseudo).getBody()).orElse(Collections.emptyList())
                .stream()
                .map(contact -> contact.getReceiverPseudo().equals(pseudo) ? contact.getSenderPseudo() : contact.getReceiverPseudo())
                .toList();


        return allUsers.stream()
                .filter(user -> !user.getPseudo().equals(pseudo))
                .filter(user -> user.isContactable() && !user.isDisable())
                .filter(user -> gender == null || user.getGender().toString().equals(gender))
                .filter(user -> birthCountry == null || user.getBirthCountry().equals(birthCountry))
                .filter(user -> motherTongue == null || user.getMotherTongue().equals(motherTongue))
                .filter(user -> minAge == null || calculateAge(user.getBirthdate()) >= minAge)
                .filter(user -> maxAge == null || calculateAge(user.getBirthdate()) <= maxAge)
                .filter(user -> !existingContacts.contains(user.getPseudo()))
                .collect(Collectors.toList());
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate;

        try {
            birthDate = LocalDate.parse(birthdate, formatter);
        } catch (DateTimeParseException e) {
            return -1;
        }

        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public User getUser(String pseudo) {
        return usersProxy.getUser(pseudo).getBody();
    }
}
