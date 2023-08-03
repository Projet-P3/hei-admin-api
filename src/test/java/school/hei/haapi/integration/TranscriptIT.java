package school.hei.haapi.integration;

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
import school.hei.haapi.endpoint.rest.model.Transcript;
import school.hei.haapi.endpoint.rest.model.TranscriptVersion;
import school.hei.haapi.endpoint.rest.security.cognito.CognitoComponent;
import school.hei.haapi.integration.conf.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static school.hei.haapi.integration.conf.TestUtils.BAD_TOKEN;
import static school.hei.haapi.integration.conf.TestUtils.MANAGER1_TOKEN;
import static school.hei.haapi.integration.conf.TestUtils.STUDENT1_ID;
import static school.hei.haapi.integration.conf.TestUtils.STUDENT1_TOKEN;
import static school.hei.haapi.integration.conf.TestUtils.TEACHER1_TOKEN;
import static school.hei.haapi.integration.conf.TestUtils.assertThrowsForbiddenException;
import static school.hei.haapi.integration.conf.TestUtils.setUpCognito;
import static school.hei.haapi.integration.conf.TestUtils.transcript1;
import static school.hei.haapi.integration.conf.TestUtils.version1;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = FeeIT.ContextInitializer.class)
@AutoConfigureMockMvc
public class TranscriptIT {
  @MockBean
  private SentryConf sentryConf;
  @MockBean
  private CognitoComponent cognitoComponentMock;

  private static ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, CourseIT.ContextInitializer.SERVER_PORT);
  }

  @BeforeEach
  void setUp() {
    setUpCognito(cognitoComponentMock);
  }

  @Test
  void student_read_transcript_by_id_ok() throws ApiException {
    ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
    TranscriptApi api = new TranscriptApi(student1Client);

    Transcript actual = api.getStudentTranscriptById(STUDENT1_ID, transcript1().getId());
    assertEquals(transcript1(), actual);
  }

  @Test
  void teacher_read_transcript_student_by_id_ok() throws ApiException {
    ApiClient teacher1Client = anApiClient(TEACHER1_TOKEN);
    TranscriptApi api = new TranscriptApi(teacher1Client);

    Transcript actual = api.getStudentTranscriptById(STUDENT1_ID, transcript1().getId());
    assertEquals(transcript1(), actual);
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

    TranscriptVersion actual = api.getStudentTranscriptVersion(STUDENT1_ID, transcript1().getId(), version1().getId());
    assertEquals(version1(), actual);
  }

  @Test
  void teacher_read_specific_version_by_transcript_ok() throws ApiException {
    ApiClient teacher1Client = anApiClient(TEACHER1_TOKEN);
    TranscriptApi api = new TranscriptApi(teacher1Client);

    TranscriptVersion actual = api.getStudentTranscriptVersion(STUDENT1_ID, transcript1().getId(), version1().getId());
    assertEquals(version1(), actual);
  }

  @Test
  void manager_read_specific_version_by_transcript_ok() throws ApiException {
    ApiClient manager1Client = anApiClient(MANAGER1_TOKEN);
    TranscriptApi api = new TranscriptApi(manager1Client);

    TranscriptVersion actual = api.getStudentTranscriptVersion(STUDENT1_ID, transcript1().getId(), version1().getId());
    assertEquals(version1(), actual);
  }

  @Test
  void read_specific_version_by_transcript_ko() throws ApiException {
    ApiClient badClient = anApiClient(BAD_TOKEN);
    TranscriptApi api = new TranscriptApi(badClient);

    assertThrowsForbiddenException(() -> api.getStudentTranscriptVersion(STUDENT1_ID, transcript1().getId(), version1().getId()));
  }

}
