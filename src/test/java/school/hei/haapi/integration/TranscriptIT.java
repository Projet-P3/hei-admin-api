package school.hei.haapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import school.hei.haapi.endpoint.rest.model.StudentTranscriptVersion;
import school.hei.haapi.endpoint.rest.model.Transcript;
import school.hei.haapi.endpoint.rest.security.cognito.CognitoComponent;
import school.hei.haapi.integration.conf.AbstractContextInitializer;
import school.hei.haapi.integration.conf.TestUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static school.hei.haapi.integration.conf.TestUtils.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = TranscriptIT.ContextInitializer.class)
@AutoConfigureMockMvc
public class TranscriptIT {
  @MockBean
  private SentryConf sentryConf;
  @MockBean
  private CognitoComponent cognitoComponentMock;

  private static ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, TranscriptIT.ContextInitializer.SERVER_PORT);
  }

    @BeforeEach
    void setUp() {
        TestUtils.setUpCognito(cognitoComponentMock);
    }

    @Test
    void student_read_transcript_ok() throws ApiException {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
        TranscriptApi api = new TranscriptApi(student1Client);

        List<Transcript> actualByStudent1 = api.getStudentTranscripts(STUDENT1_ID, null, null);
        assertEquals(2, actualByStudent1.size());
        assertTrue(actualByStudent1.contains(TestUtils.transcript1()));
    }

    @Test
    void teacher_read_transcript_ok() throws ApiException {
        ApiClient teacher1Client = anApiClient(TEACHER1_TOKEN);
        TranscriptApi api = new TranscriptApi(teacher1Client);

        List<Transcript> actualByStudent1 = api.getStudentTranscripts(STUDENT1_ID, null, null);
        assertEquals(2, actualByStudent1.size());
        assertTrue(actualByStudent1.contains(TestUtils.transcript1()));
    }

    @Test
    void create_transcript_raw() throws IOException, InterruptedException {
        final String TRANSCRIPT_RAW = "/students/"+STUDENT1_ID+"/transcripts/transcript1_id/versions/latest/raw";
        HttpClient httpClient = HttpClient.newBuilder().build();
        Path transcript_path = Paths.get("src/test/java/school/hei/haapi/files/transcript_pdf.pdf");
        byte[] transcript_byte = Files.readAllBytes(transcript_path);
        HttpRequest.BodyPublisher request_body = HttpRequest.BodyPublishers.ofByteArray(transcript_byte);

        HttpResponse<String> response =  httpClient.send(
                HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:" + ContextInitializer.SERVER_PORT+TRANSCRIPT_RAW))
                        .POST(request_body)
                        .setHeader("Content-Type", "application/pdf")
                        .header("Authorization", "Bearer "+MANAGER1_TOKEN)
                        .build(), HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        StudentTranscriptVersion responseBody = mapper.readValue(response.body(), StudentTranscriptVersion.class);

        assertNotNull(responseBody.getCreatedByUserRole());
        assertNotNull(responseBody.getRef());
        assertTrue(responseBody.getTranscriptId().equals("transcript1_id"));
    }


    @Test
    void student_write_transcript_ko() {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
        TranscriptApi api = new TranscriptApi(student1Client);

        assertThrowsForbiddenException(() -> api.crudStudentTranscripts(STUDENT1_ID, List.of()));
    }

    @Test
    void teacher_write_transcript_ko() {
        ApiClient teacher1Client = anApiClient(TEACHER1_TOKEN);
        TranscriptApi api = new TranscriptApi(teacher1Client);

        assertThrowsForbiddenException(() -> api.crudStudentTranscripts(STUDENT1_ID, List.of()));
    }

    @Test
    void manager_write_transcript_ok() throws ApiException {
        ApiClient manager1Client = anApiClient(MANAGER1_TOKEN);
        TranscriptApi api = new TranscriptApi(manager1Client);

        List<Transcript> actual = api.crudStudentTranscripts(STUDENT1_ID, List.of(transcript1()));


        assertEquals(1, actual.size());
        assertEquals(transcript1(), actual.get(0));
    }

    static class ContextInitializer extends AbstractContextInitializer {
        public static final int SERVER_PORT = anAvailableRandomPort();

        @Override
        public int getServerPort() {
            return SERVER_PORT;
        }
    }

    @Test
    void student_read_transcript_by_id_ok() throws ApiException {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
        TranscriptApi api = new TranscriptApi(student1Client);

        Transcript actual = api.getStudentTranscriptById(STUDENT1_ID, transcript1().getId());
        assertEquals(transcript1(),actual);
    }


  @Test
  void manager_read_transcript_student_by_id_ok() throws ApiException {
    ApiClient manager1Client = anApiClient(MANAGER1_TOKEN);
    TranscriptApi api = new TranscriptApi(manager1Client);

    Transcript actual = api.getStudentTranscriptById(STUDENT1_ID, transcript1().getId());
    assertEquals(transcript1(), actual);
  }

  @Test
  void read_transcript_student_by_id_ko() throws ApiException {
    ApiClient badClient = anApiClient(BAD_TOKEN);
    TranscriptApi api = new TranscriptApi(badClient);

    assertThrowsForbiddenException(() -> api.getStudentTranscriptById(STUDENT1_ID, transcript1().getId()));
  }

  @Test
  void student_read_specific_version_by_transcript_ok() throws ApiException {
    ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
    TranscriptApi api = new TranscriptApi(student1Client);

    StudentTranscriptVersion actual = api.getStudentTranscriptByVersion(STUDENT1_ID, transcript1().getId(), version1().getId());
    assertEquals(version1(), actual);
  }

  @Test
  void teacher_read_specific_version_by_transcript_ok() throws ApiException {
    ApiClient teacher1Client = anApiClient(TEACHER1_TOKEN);
    TranscriptApi api = new TranscriptApi(teacher1Client);

    StudentTranscriptVersion actual = api.getStudentTranscriptByVersion(STUDENT1_ID, transcript1().getId(), version1().getId());
    assertEquals(version1(), actual);
  }

  @Test
  void manager_read_specific_version_by_transcript_ok() throws ApiException {
    ApiClient manager1Client = anApiClient(MANAGER1_TOKEN);
    TranscriptApi api = new TranscriptApi(manager1Client);

    StudentTranscriptVersion actual = api.getStudentTranscriptByVersion(STUDENT1_ID, transcript1().getId(), version1().getId());
    assertEquals(version1(), actual);
  }

  @Test
  void read_specific_version_by_transcript_ko() throws ApiException {
    ApiClient badClient = anApiClient(BAD_TOKEN);
    TranscriptApi api = new TranscriptApi(badClient);

    assertThrowsForbiddenException(() -> api.getStudentTranscriptByVersion(STUDENT1_ID, transcript1().getId(), version1().getId()));
  }

}
