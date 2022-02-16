package com.crm.app.security;

import com.crm.app.services.UserDetailsServiceDBImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoggingAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private UserDetailsServiceDBImpl userDetailsService;

    // Password encoder
    @Bean
    public PasswordEncoder encoder() { return new BCryptPasswordEncoder(); }

    // configuration of which page can be accessed
    /*
        This spring security configuration does the following

        1. Restrict access to the Shutdown endpoint to the ACTUATOR_ADMIN role.
        2. Allow access to all other actuator endpoints.
        3. Allow access to static resources.
        4. Allow access to the home page (/).
        5. All other requests need to be authenticated.
        5. Enable http basic authentication to make the configuration complete.
           You are free to use any other form of authentication.
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        http
                .authorizeRequests()
                .requestMatchers(EndpointRequest.to(ShutdownEndpoint.class)).hasRole("ACTUATOR_ADMIN")
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .httpBasic();
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/").permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.cors()
//            .and()
//                .authorizeRequests()
//                .antMatchers(
//                        "/icons/**",
//                        "/",
//                        "/login",
//                        "/register",
//                        "/actuator/**",
//                        "/css/**",
//                        "/js/**",
//                        "/h2/**",
//                        "/api/v1/**",
//                        "/webjars/**").permitAll()
//                // In order to authorize only some part of the API to certain users
////                .antMatchers(HttpMethod.GET, "/api/v1/**").authenticated()
////                .antMatchers(HttpMethod.POST, "/api/v1/**").authenticated()
////                .antMatchers(HttpMethod.PUT, "/api/v1/**").authenticated()
////                .antMatchers(HttpMethod.DELETE, "/api/v1/**").authenticated()
//                .anyRequest().authenticated() // says all requests needs to be authenticated
//            .and()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .formLogin().loginPage("/login").permitAll()
//                .and()
//                .logout()
//                    .logoutSuccessUrl("/").permitAll()
//                .and()
//                .exceptionHandling()
//                    .accessDeniedHandler(accessDeniedHandler);
//        http.headers().frameOptions().disable();
//    }


    // authentication provider
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider (UserDetailsService service) {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//
//        authenticationProvider.setUserDetailsService(service);
//        authenticationProvider.setPasswordEncoder(encoder());
//
//        return authenticationProvider;
//    }
}
