package hu.evave.eventfinder.web.config;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String USERS_QUERY = "SELECT `name`,`password`,1 FROM `user` WHERE `name` = ?";
	private static final String ROLES_QUERY = "SELECT u.name,r.role FROM user_role r, user u WHERE u.name=? AND u.id=r.user_id";

	private DataSource dataSource;
	private UserDetailsService userDetailsService;

	@Autowired
	public SecurityConfiguration(DataSource dataSource, UserDetailsService userDetailsService) {
		this.dataSource = dataSource;
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// @formatter:off
		auth
			.jdbcAuthentication()
				.dataSource(dataSource)
					.usersByUsernameQuery(USERS_QUERY)
					.authoritiesByUsernameQuery(ROLES_QUERY);
		// @formatter:on

		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.csrf().disable()
			.formLogin()
				.loginPage("/login")
					.permitAll()
					.defaultSuccessUrl("http://localhost:4200/eventfinder/myevents", true)
					// .failureUrl("http://localhost:4200/login")
					.successHandler(new LoginSuccessHandler())
					.failureHandler(new FailureHandler())
		.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("http://localhost:4200/eventfinder")
				.permitAll()
				.invalidateHttpSession(true)
				.logoutSuccessHandler(new LogoutSuccessHandlerCustom())
		.and()
			.authorizeRequests()
				.antMatchers("/", "/login", "/rest/**", "/auth").permitAll()
				.antMatchers("/registration").anonymous()
				.antMatchers("/events").hasAuthority("SUPERADMIN")
				.antMatchers("/myevents", "/settings", "/add", "/edit/**", "/delete/**").authenticated()
				.antMatchers("/api/*, /map/*").permitAll()
				.anyRequest().permitAll();
		// @formatter:on
	}

	private final class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
				throws ServletException, IOException {
			String redirectUrl = request.getParameter("redirect");
			if (redirectUrl != null) {
				response.sendRedirect(redirectUrl);
			} else {
				super.onAuthenticationSuccess(request, response, authentication);
			}
		}
		
	}
	
	private final class FailureHandler implements AuthenticationFailureHandler {

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
				throws IOException, ServletException {
			String redirectUrl = request.getParameter("redirect");
			response.sendRedirect(redirectUrl);
		}
		
	}
	
	
	private final class LogoutSuccessHandlerCustom implements LogoutSuccessHandler {

		@Override
		public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
			String redirectUrl = request.getParameter("redirect");
			if (redirectUrl != null) {
				response.sendRedirect(redirectUrl);
			} else {
				response.sendRedirect("http://localhost:4200/eventfinder");
			}
		}
		
	}

}
