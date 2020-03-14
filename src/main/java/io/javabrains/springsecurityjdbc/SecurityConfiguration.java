package io.javabrains.springsecurityjdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // You need to tell Spring Security where to find database that stores the usernames and passwords
    DataSource dataSource; // It could be H2 database, it could be other DBs.

    @Autowired
    SecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication() // Use JDBC authentication
                .dataSource(dataSource) // Set the datasource, it will find the database that you're currently using and suto configure it.
                // Commented because we want to register users and use those information to login. No way we are predefining users here.
                // .withDefaultSchema() // You don't have to even create User and Authority table for Spring Security! That is very aweso               me  Add users below (Remember, everything here is created by Spring Security, even the User model is imported from Spring Security itself.
                // .withUser(User.withUsername("user").password("pass").roles("USER"))
                // .withUser(User.withUsername("admin").password("pass").roles("ADMIN"))
                // By default, a Spring Security database needs a Users and Authorities Database schema.
                // If I don't want a default schema? Then I have to do this (annoying):
                // override the table name and points the username of the tables:
                .usersByUsernameQuery("select username, password, enabled "
                        + "from users"
                        + "where username = ?")
                .authoritiesByUsernameQuery("select username, authority"
                        + "from authorities "
                        + "where username = ?")
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/").permitAll()
                .and()
                .formLogin();
    }

    // Temporary use NoOpPasswordEncoder for now.
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
