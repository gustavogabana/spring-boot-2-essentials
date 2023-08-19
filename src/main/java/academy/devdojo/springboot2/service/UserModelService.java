package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.UserModel;
import academy.devdojo.springboot2.mapper.UserModelMapper;
import academy.devdojo.springboot2.repository.UserModelRepository;
import academy.devdojo.springboot2.requests.userModelRequests.UserModelPostRequestBody;
import academy.devdojo.springboot2.requests.userModelRequests.UserModelPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserModelService implements UserDetailsService {

    private final UserModelRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
        return new User(userModel.getUsername(), userModel.getPassword(),
                true, true, true, true,
                userModel.getAuthorities());
    }

    public Page<UserModel> listAllPageable(Pageable page) {
        return userRepository.findAll(page);
    }

    public List<UserModel> listAll() {
        return userRepository.findAll();
    }

    public Optional<UserModel> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<UserModel> findByName(String name) {
        return userRepository.findByName(name);
    }

    public UserModel save(UserModelPostRequestBody userModelPostRequestBody) {
        UserModel userModel = UserModelMapper.INSTANCE.toUserModel(userModelPostRequestBody);
        return userRepository.save(userModel);
    }

    public void replace(UserModelPutRequestBody userModelPutRequestBody) {
        Optional<UserModel> optional = findById(userModelPutRequestBody.getId());
        if (optional.isPresent()) {
            UserModel userModelSaved = optional.get();
            UserModel userModel = UserModelMapper.INSTANCE.toUserModel(userModelPutRequestBody);
            userModel.setId(userModelSaved.getId());
            userRepository.save(userModel);
        }
    }

    public void delete(Long id) {
        Optional<UserModel> optional = findById(id);
        if (optional.isPresent()) {
            userRepository.deleteById(id);
        }
    }

    public List<String> listRoles() {
        return List.of("ROLE_USER", "ROLE_ADMING");
    }
}
