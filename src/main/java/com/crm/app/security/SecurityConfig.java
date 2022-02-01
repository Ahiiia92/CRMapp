package com.crm.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoggingAccessDeniedHandler accessDeniedHandler;

    // configuration of which page can be accessed
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors()
            .and()
                .authorizeRequests()
                .antMatchers(
                        "/icons/**",
                        "/",
                        "/login",
                        "/css/**",
                        "/js/**",
                        "/h2/**",
                        "/api/v1/**",
                        "/webjars/**").permitAll()
                // In order to authorize only some part of the API to certain users
//                .antMatchers(HttpMethod.GET, "/api/v1/**").authenticated()
//                .antMatchers(HttpMethod.POST, "/api/v1/**").authenticated()
//                .antMatchers(HttpMethod.PUT, "/api/v1/**").authenticated()
//                .antMatchers(HttpMethod.DELETE, "/api/v1/**").authenticated()
//                .antMatchers(HttpMethod.GET, "/api/v1/contacts/**").hasRole("SuperAdmin")
//                .antMatchers(HttpMethod.POST, "/api/v1/contacts/").hasRole("SuperAdmin")
//                .antMatchers(HttpMethod.PUT, "/api/v1/contacts/**").hasRole("SuperAdmin")
//                .antMatchers(HttpMethod.DELETE, "/api/v1/contacts/**").hasRole("SuperAdmin")
//                .antMatchers("/api/v1/contacts/**").hasRole("Admin")
                .anyRequest().authenticated() // says all requests needs to be authenticated
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout()
                    .logoutSuccessUrl("/").permitAll()
                .and()
                .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler);
        http.headers().frameOptions().disable();
    }

    /* Comment out for now.
    // Create Some admin access
    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// add our users for in memory authentication
        User.UserBuilder users = User.withDefaultPasswordEncoder();

		//UserBuilder users = User();

		auth.inMemoryAuthentication()
			.withUser("testAdmin").password("password123").roles("Admin")
                .and()
			.withUser("testSuperAdmin").password("test123").roles("SuperAdmin")
		        .and()
			.withUser("admin").password("password123").roles("ADMIN");
	}

     */

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
