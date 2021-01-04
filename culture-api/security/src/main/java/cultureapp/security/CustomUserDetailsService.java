package cultureapp.security;

import cultureapp.domain.account.Account;
import cultureapp.domain.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("No user found with username '%s'.", email)));
       return new CustomUserDetails(account);
    }

//    public void changePassword(String oldPassword, String newPassword) {
//        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
//        String username = ((User) currentUser.getPrincipal()).getEmail();
//
//        if (authenticationManager != null) {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
//        } else {
//            return;
//        }
//        User user = (User) loadUserByUsername(username);
//
//        user.setPassword(passwordEncoder.encode(newPassword));
//        userRepository.save(user);
//    }

}
