package school.hei.haapi.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import school.hei.haapi.SentryConf;
import school.hei.haapi.endpoint.rest.api.PayingApi;
import school.hei.haapi.endpoint.rest.api.TranscriptApi;
import school.hei.haapi.endpoint.rest.client.ApiClient;
import school.hei.haapi.endpoint.rest.client.ApiException;
import school.hei.haapi.endpoint.rest.security.cognito.CognitoComponent;
import school.hei.haapi.integration.conf.AbstractContextInitializer;
import school.hei.haapi.integration.conf.TestUtils;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static school.hei.haapi.integration.conf.TestUtils.*;
import static school.hei.haapi.integration.conf.TestUtils.assertThrowsApiException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = TranscriptRawIT.ContextInitializer.class)
@AutoConfigureMockMvc
class TranscriptRawIT {
    @MockBean
    private SentryConf sentryConf;
    @MockBean
    private CognitoComponent cognitoComponentMock;

    private static ApiClient anApiClient(String token) {
        return TestUtils.anApiClient(token, TranscriptRawIT.ContextInitializer.SERVER_PORT);
    }

    @BeforeEach
    void setUp() {
        TestUtils.setUpCognito(cognitoComponentMock);
    }

    @Test
    void student_read_transcript_raw_ok() throws ApiException, IOException {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
        TranscriptApi api = new TranscriptApi(student1Client);


        File pdfFile = api.getStudentTranscriptVersionPdf(STUDENT1_ID, TRANSCRIPT1_ID, VERSION1_ID);

        assertNotNull(pdfFile);

    }

    @Test
    void student_read_transcript_raw_ko() {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
        TranscriptApi api = new TranscriptApi(student1Client);

        assertThrowsApiException(
                "{\"type\":\"403 FORBIDDEN\",\"message\":\"Access is denied\"}",
                () -> api.getStudentTranscriptVersionPdf(STUDENT2_ID, TRANSCRIPT1_ID, VERSION1_ID));
        assertThrowsApiException(
                "{\"type\":\"403 FORBIDDEN\",\"message\":\"Access is denied\"}",
                () -> api.getStudentTranscriptVersionPdf(STUDENT2_ID, null, null));
        assertThrowsApiException(
                "{\"type\":\"403 FORBIDDEN\",\"message\":\"Access is denied\"}",
                () -> api.getStudentTranscriptVersionPdf(null, null, null));
    }

    @Test
    void teacher_read_transcript_raw_ok() throws ApiException, IOException {
        ApiClient student1Client = anApiClient(TEACHER1_TOKEN);
        TranscriptApi api = new TranscriptApi(student1Client);


        File pdfFile = api.getStudentTranscriptVersionPdf(STUDENT1_ID, TRANSCRIPT1_ID, VERSION1_ID);

        assertNotNull(pdfFile);

    }

    @Test
    void manager_read_transcript_raw_ok() throws ApiException, IOException {
        ApiClient student1Client = anApiClient(MANAGER1_TOKEN);
        TranscriptApi api = new TranscriptApi(student1Client);


        File pdfFile = api.getStudentTranscriptVersionPdf(STUDENT1_ID, TRANSCRIPT1_ID, VERSION1_ID);

        assertNotNull(pdfFile);
    }


    static class ContextInitializer extends AbstractContextInitializer {
        public static final int SERVER_PORT = anAvailableRandomPort();

        @Override
        public int getServerPort() {
            return SERVER_PORT;
        }
    }
}


