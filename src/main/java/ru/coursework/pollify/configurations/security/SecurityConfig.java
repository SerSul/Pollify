package ru.coursework.pollify.configurations.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authConfiguration;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationManager authManager,
                                                   AuthenticationSuccessHandler authenticationSuccessHandler,
                                                   LogoutHandler logoutHandler) throws Exception {
        http
                .authorizeHttpRequests(mr -> mr
                        .requestMatchers("/v3/api-docs", "/v3/api-docs/**", "/swagger-ui", "/swagger-ui/**").hasAnyAuthority("ADMIN")
                        .anyRequest().permitAll())
                .formLogin(flc -> flc
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .successHandler(authenticationSuccessHandler)
                        .permitAll())
                .logout(lc -> lc
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessUrl("/login?l_logout")
                        .clearAuthentication(true)
                        .permitAll())
                .exceptionHandling(ehc -> ehc
                        .accessDeniedPage("/403"))
                .rememberMe(rmc -> rmc
                        .key("adaptMSystems2485_1"))
                .csrf(cc -> cc
                        .ignoringRequestMatchers("/**"))
                .requiresChannel(c -> c.anyRequest().requiresInsecure())
                .cors(Customizer.withDefaults())
                .authenticationManager(authManager)
                .sessionManagement(c -> c
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .maximumSessions(-1)
                        .sessionRegistry(sessionRegistry())
                        .expiredUrl("/login?logout"));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LogoutHandler logoutSuccessHandler() {
        return new SecurityContextLogoutHandler();
    }

    @Bean
    public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
        return new RememberMeAuthenticationProvider("adaptMSystems2485_1");
    }

}
