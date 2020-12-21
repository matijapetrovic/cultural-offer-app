package cultureapp.domain.user;

import static cultureapp.common.AuthenticationTestData.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RegularUserRepositoryIntegrationTest {

    @Autowired
    private RegularUserRepository regularUserRepository;

    @Test
    public void givenUserExistsFindWillReturnNotEmpty() {
        Optional<RegularUser> regularUser = regularUserRepository.findByAccountIdWithSubscriptions(3L);
        assertFalse(regularUser.isEmpty());
    }

    @Test
    public void givenUserDoesntExistsFindWillReturnNotEmpty() {
        Optional<RegularUser> regularUser = regularUserRepository.findByAccountIdWithSubscriptions(5L);
        assertTrue(regularUser.isEmpty());
    }

}
