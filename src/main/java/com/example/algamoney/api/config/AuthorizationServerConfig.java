package com.example.algamoney.api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.example.algamoney.api.config.token.CustomTokenEnhancer;

@Profile("oauth-security")
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
		
	@Autowired
	private AuthenticationManager authenticationManager;
	
	//Autoriza o cliente
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient("angular")
			.secret("$2a$10$G1j5Rf8aEEiGc/AET9BA..xRR.qCpOUzBZoJd8ygbGy6tb3jsMT9G")
			.scopes("read", "write")
			.authorizedGrantTypes("password", "refresh_token")
			.accessTokenValiditySeconds(1800)
			.refreshTokenValiditySeconds(3600 * 24) //token valido por 1 dia
		.and()
		.withClient("mobile")
		.secret("$2y$12$HBjbHXZxuQXBbAgJKUQUtOXTJ3ZG7nj.FVXsXqvrHqNWbZc1C8N.W")
		.scopes("read") //apenas leitura
		.authorizedGrantTypes("password", "refresh_token")
		.accessTokenValiditySeconds(1800)
		.refreshTokenValiditySeconds(3600 * 24); //token valido por 1 dia
		
	}
	
	//Armazenamento de Tokens
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain(); //token com mais detalhamento
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter())); //cadeia de tokens
		
		endpoints
			.tokenStore(tokenStore())
			.tokenEnhancer(tokenEnhancerChain)
			.reuseRefreshTokens(false)
			.authenticationManager(authenticationManager);
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("algaworks");
		return accessTokenConverter;
	}

	@Bean //cria uma única vez
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter()); //não armazena o token
	}
	
	@Bean
	public TokenEnhancer tokenEnhancer() { //customização para incrementar o token
	    return new CustomTokenEnhancer();
	}
}
