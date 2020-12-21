package cultureapp.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegularUserRepository extends JpaRepository<RegularUser, Long> {
        Optional<RegularUser> findByAccountId(Long accountId);

        // Ako vam pada na lazy excewption iskristite ovo
        @Query("select ru from RegularUser ru left outer join fetch ru.culturalOffers co where ru.account.id = :accountId")
        Optional<RegularUser> findByAccountIdWithSubscriptions(@Param(value="accountId") Long accountId);
}
