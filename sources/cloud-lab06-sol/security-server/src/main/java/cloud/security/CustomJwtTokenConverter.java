package cloud.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class CustomJwtTokenConverter extends JwtAccessTokenConverter {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> additionalInfo = new HashMap<>();
		additionalInfo.put("organization", authentication.getName() + System.currentTimeMillis());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		// Encode Token to JWT
		String encoded = super.encode(accessToken, authentication);
		// Set JWT as value of the token
		((DefaultOAuth2AccessToken) accessToken).setValue(encoded);
		accessToken.getAdditionalInformation().clear();
		return accessToken;
	}

}