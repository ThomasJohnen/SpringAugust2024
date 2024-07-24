package socialtaal.profiles;

import org.springframework.stereotype.Service;
import socialtaal.profiles.models.Profile;
import socialtaal.profiles.repository.ProfilesRepository;

@Service
public class ProfilesServices {
    private final ProfilesRepository repository;

    public ProfilesServices(ProfilesRepository aNewrepository) {
        this.repository = aNewrepository;
    }

    public Profile getProfile(String username) {
        return repository.findById(username).orElse(null);
    }

    public Profile createProfile(Profile profile) {
        if(repository.existsById(profile.getUsername())) return null;
        return repository.save(profile);
    }
    public Profile updateProfile(Profile profile) {
        Profile profileToUpdate = repository.findById(profile.getUsername()).orElse(null);
        if(profileToUpdate == null) return null;
        profileToUpdate.setBiography(profile.getBiography());
        profileToUpdate.setContactable(profile.isContactable());
        return repository.save(profileToUpdate);
    }
}
