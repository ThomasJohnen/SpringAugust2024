package socialtaal.profiles;

import org.springframework.stereotype.Service;
import socialtaal.profiles.models.Profile;
import socialtaal.profiles.models.User;
import socialtaal.profiles.repository.ProfilesRepository;

import java.awt.event.ItemEvent;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class ProfilesServices {
    private final ProfilesRepository repository;

    public ProfilesServices(ProfilesRepository aNewrepository) {
        this.repository = aNewrepository;
    }

    public List<Profile> getAllProfiles() {
        Iterable<Profile> listProfile = repository.findAll();
        return StreamSupport.stream(listProfile.spliterator(), false).toList();
    }

    public Profile getProfile(String pseudo) {
        return repository.findById(pseudo).orElse(null);
    }

    public Profile createProfile(Profile profile) {
        return repository.save(profile);
    }

    public Profile updateProfile(Profile profile) {
        Profile oldProfile = getProfile(profile.getPseudo());
        oldProfile.setBiography(profile.getBiography());
        oldProfile.setContactable(profile.isContactable());
        return repository.save(oldProfile);
    }

    public void deleteProfile(String pseudo) {
        Profile profileToDelete = getProfile(pseudo);
        profileToDelete.setContactable(false);
        repository.save(profileToDelete);

    }
}
