package com.web.BebusWebsite.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/registration").permitAll()
                .antMatchers("/cinema").permitAll()
                .antMatchers("/movie-show").permitAll()
                .antMatchers("/movie").permitAll()
                .antMatchers("/hall").hasRole("ADMIN")
                .antMatchers("/studio").permitAll()
                .antMatchers("/cinema/create").hasRole("ADMIN")
                .antMatchers("/cinema/edit").hasRole("ADMIN")
                .antMatchers("/movie-show/create").hasRole("ADMIN")
                .antMatchers("/movie-show/create-next").hasRole("ADMIN")
                .antMatchers("/movie-show/edit").hasRole("ADMIN")
                .antMatchers("/movie/create").hasRole("ADMIN")
                .antMatchers("/movie/edit").hasRole("ADMIN")
                .antMatchers("/hall/create").hasRole("ADMIN")
                .antMatchers("/hall/edit").hasRole("ADMIN")
                .antMatchers("/studio/create").hasRole("ADMIN")
                .antMatchers("/studio/edit").hasRole("ADMIN")
                .antMatchers("/cinema/details").hasAnyRole("USER","ADMIN")
                .antMatchers("/movie-show/details").hasAnyRole("USER","ADMIN")
                .antMatchers("/movie/details").hasAnyRole("USER","ADMIN")
                .antMatchers("/hall/details").hasRole("ADMIN")
                .antMatchers("/studio/details").hasAnyRole("USER","ADMIN")
                .antMatchers("/cinema/delete").hasRole("ADMIN")
                .antMatchers("/movie-show/delete").hasRole("ADMIN")
                .antMatchers("/movie/delete").hasRole("ADMIN")
                .antMatchers("/hall/delete").hasRole("ADMIN")
                .antMatchers("/studio/delete").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf().disable()
                .exceptionHandling().accessDeniedPage("/access/denied");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(encoder())
                .usersByUsernameQuery("select username, password, active from user_entity where username = ?")
                .authoritiesByUsernameQuery("select u.username, ur.roles from user_entity u inner join user_entity_roles ur on u.id = ur.user_entity_id where u.username=?");
    }
}