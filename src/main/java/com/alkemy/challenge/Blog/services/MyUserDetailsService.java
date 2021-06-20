package com.alkemy.challenge.Blog.services;
import com.alkemy.challenge.Blog.models.MyUserDetails;
import com.alkemy.challenge.Blog.models.User;
import com.alkemy.challenge.Blog.repository.UserRepository;
import com.alkemy.challenge.Blog.util.EmailChecker;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    private EmailChecker emailChecker;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    public MyUserDetailsService() {
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("The user: " + username + " doesn't exist.")
        );
        return new MyUserDetails(user);
    }

    public User registerUser(String email, String password) throws IllegalArgumentException{
        //Si el email no es valido, se lanza excepción.
        if(!emailChecker.isValid(email)){
            throw new IllegalArgumentException("You need put a valid email.");
        }
        //Validamos de que el usuario no exista.
        boolean userExists = userRepository.findByEmail(email).isPresent();
        if(userExists){
            throw new IllegalStateException("User is already registered");
        }
        User auxUser = new User();
        auxUser.setEmail(email);
        auxUser.setPassword(bcryptEncoder.encode(password));
        System.out.println(auxUser.getPassword());
        System.out.println(bcryptEncoder.encode(password));
        return userRepository.save(auxUser);
    }
}
