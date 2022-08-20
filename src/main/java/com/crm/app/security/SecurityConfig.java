package com.crm.app.security;

import com.crm.app.services.UserDetailsServiceDBImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoggingAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private UserDetailsServiceDBImpl userDetailsServiceDB;

    // configuration of which page can be accessed
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.authorizeRequests().antMatchers("/api/auth/signup").permitAll()
                .anyRequest().authenticated()
        .and()
        .httpBasic();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    // Create Some admin access
    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceDB).passwordEncoder(encoder());
//        // add our users for in memory authentication
//        User.UserBuilder users = User.withDefaultPasswordEncoder();
//
//		User.UserBuilder users = User();
//
		auth.inMemoryAuthentication()
			.withUser("testAdmin").password(encoder().encode("password123")).roles("USER", "ADMIN")
                .and()
			.withUser("testSuperAdmin").password("test123").roles("SuperAdmin")
		        .and()
			.withUser("admin").password("password123").roles("USER", "ADMIN")
                .and()
            .withUser("anonymousUser").password("123456").roles("USER");
	}


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // authentication provider
    @Bean
    public DaoAuthenticationProvider authenticationProvider(@Qualifier("UserDetailServiceSecurityCore") UserDetailsService service) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(service);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
