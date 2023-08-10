package school.hei.haapi.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import school.hei.haapi.SentryConf;
import school.hei.haapi.endpoint.rest.api.TranscriptApi;
import school.hei.haapi.endpoint.rest.client.ApiClient;
import school.hei.haapi.endpoint.rest.client.ApiException;
import school.hei.haapi.endpoint.rest.model.StudentTranscriptClaim;
import school.hei.haapi.endpoint.rest.security.cognito.CognitoComponent;
import school.hei.haapi.integration.conf.AbstractContextInitializer;
import school.hei.haapi.integration.conf.TestUtils;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static school.hei.haapi.integration.conf.TestUtils.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = StudentTranscriptVersionClaimIT.ContextInitializer.class)
@AutoConfigureMockMvc
public class StudentTranscriptVersionClaimIT {
    @MockBean
    private SentryConf sentryConf;

    @MockBean
    private CognitoComponent cognitoComponentMock;

    private ApiClient anApiClient(String token) {
        return TestUtils.anApiClient(token, ContextInitializer.SERVER_PORT);
    }

    @BeforeEach
    public void setUp() {
        setUpCognito(cognitoComponentMock);
    }

    @Test
    void student_read_transcript_version_claim_ok() throws ApiException {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
        TranscriptApi api = new TranscriptApi(student1Client);

        StudentTranscriptClaim claim = api.getStudentClaimOfTranscriptVersion(
          "student1_id",
          "transcript1_id",
          "version1_id",
          "claim1_id");

        assertEquals(studentTranscriptClaim1(),claim);
    }
    @Test
    void student_read_all_transcript_version_claim_ok() throws ApiException {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
        TranscriptApi api = new TranscriptApi(student1Client);

        List<StudentTranscriptClaim> claim = api.getStudentTranscriptClaims(
          "student1_id",
          "transcript1_id",
          "version1_id",
          1,
          10);

        assertTrue(claim.contains(studentTranscriptClaim1()));
    }
    /*
    @Test
    void teacher_read_transcript_version_claim_ok() throws ApiException {
        ApiClient teacher1Client = anApiClient(TEACHER1_TOKEN);
        TranscriptApi api = new TranscriptApi(teacher1Client);

        StudentTranscriptClaim claim = api.getStudentClaimOfTranscriptVersion(
          "student1_id",
          "transcript1_id",
          "version1_id",
          "claim1_id");

        assertEquals(studentTranscriptClaim1(), claim);
        assertEquals(claim.getId(), "claim1_id");
        assertEquals(claim.getTranscriptVersionId(), "version1_id");
    }
    */
    @Test
    void manager_read_transcript_version_claim_ok() throws ApiException {
        ApiClient manager1Client = anApiClient(MANAGER1_TOKEN);
        TranscriptApi api = new TranscriptApi(manager1Client);

        StudentTranscriptClaim claim = api.getStudentClaimOfTranscriptVersion(
          "student1_id",
          "transcript1_id",
          "version1_id",
          "claim1_id");

        assertEquals(studentTranscriptClaim1(), claim);
        assertEquals(claim.getId(), "claim1_id");
        assertEquals(claim.getTranscriptVersionId(), "version1_id");
    }

    /*
    @Test
    void student_write_transcript_version_claim_ok() throws ApiException {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
        TranscriptApi api = new TranscriptApi(student1Client);

        StudentTranscriptClaim claim = api.getStudentClaimOfTranscriptVersion(
          "student1_id",
          "transcript1_id",
          "version1_id",
          "claim1_id");

        StudentTranscriptClaim update = api.putStudentClaimsOfTranscriptVersion(
          "student1_id",
          "transcript1_id",
          "version1_id",
          "claim1_id",
          claim
          );

        assertEquals(claim, update);
    }


     */
    static class ContextInitializer extends AbstractContextInitializer {
        public static final int SERVER_PORT = anAvailableRandomPort();

        @Override
        public int getServerPort() {
            return SERVER_PORT;
        }
    }
}
