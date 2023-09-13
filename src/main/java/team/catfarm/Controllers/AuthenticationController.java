package team.catfarm.Controllers;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import team.catfarm.DTO.Input.AuthenticationRequest;
import team.catfarm.DTO.Output.AuthenticationResponse;
import team.catfarm.Exceptions.AccessDeniedException;
import team.catfarm.Exceptions.ResourceNotFoundException;
import team.catfarm.Models.User;
import team.catfarm.Repositories.UserRepository;
import team.catfarm.Services.CustomUserDetailsService;
import team.catfarm.Utils.JwtUtil;

import java.security.Principal;
import java.util.Optional;

@CrossOrigin
@RestController
public class AuthenticationController {

    /*inject authentionManager, userDetailService en jwtUtil*/
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthenticationController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, UserRepository userRepository, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    /*
         Deze methode geeft de principal (basis user gegevens) terug van de ingelogde gebruiker
     */
    @GetMapping(value = "/authenticated")
    public ResponseEntity<Object> authenticated(Authentication authentication, Principal principal) {
        return ResponseEntity.ok().body(principal);
    }

    /*
    Deze methode geeft het JWT token terug wanneer de gebruiker de juiste inloggegevens op geeft.
     */
    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + username));

        if (user == null || user.getAuthorities() == null || user.getAuthorities().isEmpty()) {
            throw new AccessDeniedException("Access denied: User has no assigned roles");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        }
        catch (BadCredentialsException ex) {
            throw new Exception("Incorrect username or password", ex);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}