package ru.th.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/cashier/**").hasRole("cashier")
				.requestMatchers("/expert/**").hasRole("expert")
				.requestMatchers("/contentmaker/**").hasRole("contentmaker")
				.requestMatchers("/**").permitAll()
			)
			.httpBasic(Customizer.withDefaults())
			.formLogin(Customizer.withDefaults());
		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		UserDetails user = User.withUsername("customer")
			.password(encoder.encode("password"))
			.roles("customer")
			.build();
		UserDetails cashier = User.withUsername("cashier")
			.password(encoder.encode("cashier"))
			.roles("cashier", "customer")
			.build();
		UserDetails expert = User.withUsername("expert")
			.password(encoder.encode("expert"))
			.roles("expert", "customer")
			.build();
		UserDetails contentmaker = User.withUsername("contentmaker")
			.password(encoder.encode("contentmaker"))
			.roles("contentmaker", "customer")
			.build();
		return new InMemoryUserDetailsManager(user, cashier, expert, contentmaker);
	}

}
