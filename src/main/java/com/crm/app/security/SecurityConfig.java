package com.crm.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoggingAccessDeniedHandler accessDeniedHandler;

    // configuration of which page can be accessed
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/icons/**",
                        "/",
                        "/login",
                        "/css/**",
                        "/js/**",
                        "/h2/**",
//                        "/api/v1/contacts",
                        "/webjars/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/v1/contacts").hasRole("SuperAdmin")
//                .antMatchers(HttpMethod.GET, "/api/v1/contacts/**").hasRole("SuperAdmin")
//                .antMatchers(HttpMethod.POST, "/api/v1/contacts/").hasRole("SuperAdmin")
//                .antMatchers(HttpMethod.PUT, "/api/v1/contacts/**").hasRole("SuperAdmin")
//                .antMatchers(HttpMethod.DELETE, "/api/v1/contacts/**").hasRole("SuperAdmin")
//                .antMatchers("/api/v1/contacts/**").hasRole("Admin")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .disable()
//                .and()
//                .logout()
//                    .invalidateHttpSession(true)
//                    .clearAuthentication(true)
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                    .logoutSuccessUrl("/").permitAll()
//                .and()
                .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler);

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    // Create Some admin access
//    @Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//		// add our users for in memory authentication
//        User.UserBuilder users = User.withDefaultPasswordEncoder();
//
//		//UserBuilder users = User();
//
//		auth.inMemoryAuthentication()
//			.withUser("testAdmin").password("password123").roles("Admin")
//                .and()
//			.withUser("testSuperAdmin").password("test123").roles("SuperAdmin")
//		        .and()
//			.withUser("admin").password("password123").roles("ADMIN");
//	}

    // authentication provider
    @Bean
    public DaoAuthenticationProvider authenticationProvider (UserDetailsService service) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(service);
        authenticationProvider.setPasswordEncoder(encoder());

        return authenticationProvider;
    }

    // Password encoder
    @Bean
    public PasswordEncoder encoder() { return new BCryptPasswordEncoder(); }
}
