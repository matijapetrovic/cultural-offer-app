package cultureapp.domain.regular_user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegularUserRepository extends JpaRepository<RegularUser, Long> {
    Optional<RegularUser> findByAccountId(Long accountId);
}
