package cultureapp.rest.image;


import cultureapp.domain.image.ImageRepository;
import cultureapp.rest.ControllerIntegrationTestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;

import static cultureapp.common.AuthenticationTestData.EXISTING_ADMINISTRATOR_EMAIL;
import static cultureapp.common.AuthenticationTestData.EXISTING_ADMINISTRATOR_PASSWORD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class ImageControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ImageRepository imageRepository;

    @Test
    public void givenAuthorizedUserAndValidImagesThenImagesPostWillSucceed() {
        long count = imageRepository.count();


        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();

        List<String> images = List.of("test-image.jpg", "test-image.jpg");
        images.forEach(image -> params.add("images", new ClassPathResource(image)));

        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> entity =
                new HttpEntity<>(params, headers);

        ResponseEntity<Long[]> response = restTemplate.exchange(
                "/api/images",
                HttpMethod.POST,
                entity,
                Long[].class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(images.size() ,response.getBody().length);

        assertEquals(count + images.size(), imageRepository.count());
        imageRepository.deleteAll(imageRepository.findAllById(Arrays.asList(response.getBody())));
    }

    @Test
    public void givenUnauthorizedUserThenImagesPostWillReturnUnauthorized() {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();

        List<String> images = List.of("test-image.jpg", "test-image.jpg");
        images.forEach(image -> params.add("images", new ClassPathResource(image)));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> entity =
                new HttpEntity<>(params, headers);

        ResponseEntity<Object> response = restTemplate.exchange(
                "/api/images",
                HttpMethod.POST,
                entity,
                Object.class
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void givenInvalidMultiPartFilesThenImagesPostWillReturnBadRequest() {
        long count = imageRepository.count();

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();

        List<String> images = List.of("invalid-file.txt", "invalid-file.txt");
        images.forEach(image -> params.add("images", new ClassPathResource(image)));

        String token = ControllerIntegrationTestUtil.login(restTemplate, EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);
        HttpHeaders headers = ControllerIntegrationTestUtil.getHeaders(token);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> entity =
                new HttpEntity<>(params, headers);

        ResponseEntity<Object> response = restTemplate.exchange(
                "/api/images",
                HttpMethod.POST,
                entity,
                Object.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(count, imageRepository.count());

    }
}
