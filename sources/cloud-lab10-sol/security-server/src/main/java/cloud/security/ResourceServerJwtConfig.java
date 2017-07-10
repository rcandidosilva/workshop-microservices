package cloud.security;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
public class ResourceServerJwtConfig {
	
	@Autowired TokenStore tokenStore;
	
//	@Bean
//	public JwtAccessTokenConverter accessTokenConverter() {
//		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//		converter.setSigningKey("123");
//		return converter;
//	}
	
	public JwtAccessTokenConverter accessTokenConverter() {
	    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	    Resource resource = new ClassPathResource("public.txt");
	    String publicKey = null;
	    try {
	        publicKey = IOUtils.toString(resource.getInputStream());
	    } catch (final IOException e) {
	        throw new RuntimeException(e);
	    }
	    converter.setVerifierKey(publicKey);
	    return converter;
	}
		
	@Bean
	@Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }
	
}