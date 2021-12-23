package com.crm.app.security;

import com.crm.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoggingAccessDeniedHandler accessDeniedHandler;

    // configuration of which page can be accessed
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/", "/login*").permitAll()
                .anyRequest().authenticated() // says all requests needs to be authenticated
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/perform_login")
                    .defaultSuccessUrl("/", true)
                    .failureUrl("/error")
                .and()
                    .logout()
                    .logoutUrl("/perform_logout")
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/").permitAll()
                .and()
                .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler);

        http.headers().frameOptions().disable();
    }

    // authentication provider
    @Bean
    public DaoAuthenticationProvider authenticationProvider (UserService service) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService((UserDetailsService) service);
        authenticationProvider.setPasswordEncoder(encoder());

        return authenticationProvider;
    }

    // Password encoder
    @Bean
    public PasswordEncoder encoder() { return new BCryptPasswordEncoder(); }
}

// https://octoperf.com/blog/2018/03/08/securing-rest-api-spring-security/#testing-the-application-1