package cultureapp.domain.user;

import cultureapp.domain.user.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    Optional<Administrator> findByAccountId(Long accountId);
    Optional<Administrator> findById(Long id);
}
