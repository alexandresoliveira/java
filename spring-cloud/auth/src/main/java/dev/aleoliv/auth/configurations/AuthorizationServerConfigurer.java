package dev.aleoliv.auth.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	public AuthorizationServerConfigurer(
			AuthenticationManager authenticationManager, 
			UserDetailsService userDetailsService, 
			PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
			.inMemory()
			.withClient("loja")
			.secret(passwordEncoder.encode("lojapwd"))
			.authorizedGrantTypes("password")
			.scopes("web", "mobile");
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.authenticationManager(authenticationManager)
			.userDetailsService(userDetailsService);
	}
}
