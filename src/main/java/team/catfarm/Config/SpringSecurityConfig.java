package team.catfarm.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import team.catfarm.Filter.JwtRequestFilter;
import team.catfarm.Services.CustomUserDetailsService;

/*  Deze security is niet de enige manier om het te doen.
    In de andere branch van deze github repo staat een ander voorbeeld
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    public final CustomUserDetailsService customUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    // PasswordEncoderBean. Deze kun je overal in je applicatie injecteren waar nodig.
    // Je kunt dit ook in een aparte configuratie klasse zetten.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Authenticatie met customUserDetailsService en passwordEncoder
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }



    // Authorizatie met jwt
    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeHttpRequests()
                // Wanneer je deze uncomments, staat je hele security open. Je hebt dan alleen nog een jwt nodig.
//                .requestMatchers("/**").permitAll()
                //users
                .requestMatchers(HttpMethod.POST, "/users/create").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/{email}").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.GET, "/users/enabled").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.PUT, "/users/update/{email}").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.PUT, "/users/update_password/{email}").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.PUT, "/users/{email}/rsvp/{event_id}").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.PUT, "/users/{email}/task/{task_id}").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.PUT, "/users/{email}/remove/task/{task_id}").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.PUT, "/users/{email}/usercreatesevent/{event_id}").hasAnyRole("CAT", "LION")
                .requestMatchers(HttpMethod.PUT, "/users/{email}/usercreatestask/{task_id}").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.DELETE, "/users/delete/{email}").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.GET, "/users/authorities/{email}").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.POST, "/users/add_authorities/{email}").hasAnyRole("LION")
                .requestMatchers(HttpMethod.DELETE, "/users/remove_authorities/{email}/{authority}").hasRole("LION")
                //files
                .requestMatchers(HttpMethod.POST, "/files/upload").hasAnyRole("CAT", "LION")
                .requestMatchers(HttpMethod.GET, "/files/download/{id}").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.GET, "/files/{id}").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.PUT, "/files/{file_id}/profile_picture/{user_id}").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.DELETE, "/files/delete/{id}").hasAnyRole("CAT", "LION")
                //events
                .requestMatchers(HttpMethod.POST, "/events/add").hasAnyRole("CAT", "LION")
                .requestMatchers(HttpMethod.GET, "/events/{id}").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.GET, "/events/{start}/{end}").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.PUT, "/events/update/{id}").hasAnyRole("CAT", "LION")
                .requestMatchers(HttpMethod.PUT, "/events/{id}/tasks/{task_id}").hasAnyRole("CAT", "LION")
                .requestMatchers(HttpMethod.DELETE, "/events/delete/{id}").hasAnyRole("CAT", "LION")
                //tasks
                .requestMatchers(HttpMethod.POST, "/tasks/add").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.GET, "/tasks/{id}").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.GET, "/tasks/user_tasks/{user_email}").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.PUT, "/tasks/update/{id}").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.PUT, "/tasks/{id}/event/{event_id}").hasAnyRole("CAT", "LION")
                .requestMatchers(HttpMethod.PUT, "/tasks/assignfiles/{id}").hasAnyRole("CAT", "LION")
                .requestMatchers(HttpMethod.DELETE, "/tasks/delete/{id}").hasAnyRole("CAT", "LION")
                //Todos
                .requestMatchers(HttpMethod.POST, "/task/todos/{task_id}/create").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.GET, "/task/todos/{task_id}/get").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.PUT, "/task/todos/{task_id}/update").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers(HttpMethod.DELETE, "/task/todos/{task_id}/delete").hasAnyRole("KITTEN", "CAT", "LION")
                // Je mag meerdere paths tegelijk definieren
                .requestMatchers("/users", "/events", "/tasks", "/files").hasAnyRole("KITTEN", "CAT", "LION")
                .requestMatchers("/authenticated").authenticated()
                .requestMatchers("/authenticate").permitAll()
                .anyRequest().denyAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}