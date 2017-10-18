package hu.evave.eventfinder.web;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity(debug=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	DataSource dataSource;
	
	@Autowired
	UserDetailsService userDetailsService;
	
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery("select `name`,`password`,1 from `user` where `name` = ?")
				.authoritiesByUsernameQuery("select u.name,r.role FROM user_role r, user u where u.name=? AND u.id=r.user_id");	
		
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.formLogin().permitAll()
				.and()
				.authorizeRequests()
					.antMatchers("/", "/login", "/rest/**").permitAll()
					.and()
				.authorizeRequests()
					.antMatchers("/registration").anonymous()
					.and()
				.authorizeRequests()
					.antMatchers("/api/*").permitAll()
					.and()
				.authorizeRequests()
					.antMatchers("/myevents", "/settings", "/add", "/edit/**", "/delete/**").authenticated()
					.anyRequest().authenticated()
					.and()
				.authorizeRequests()
					.antMatchers("/events").hasAuthority("SUPERADMIN")
					.anyRequest().hasAuthority("SUPERADMIN")
					.and()
				.formLogin()
	                .loginPage("/login")
	                .permitAll()
	                .defaultSuccessUrl("/myevents", true)
	                .and()
	            .logout()
	                .permitAll()
	                .invalidateHttpSession(true)
	                .and()
	                ;
	}
	
	

	
	
}
