package cloud.security.details;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthClientDetailsRepository extends JpaRepository<AuthClientDetails, Long> {

	AuthClientDetails findByClientId(String clientId);
	
}