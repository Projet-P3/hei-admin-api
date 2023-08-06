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
import school.hei.haapi.endpoint.rest.security.cognito.CognitoComponent;
import school.hei.haapi.integration.conf.AbstractContextInitializer;
import school.hei.haapi.integration.conf.TestUtils;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static school.hei.haapi.integration.conf.TestUtils.STUDENT1_TOKEN;
import static school.hei.haapi.integration.conf.TestUtils.TRANSCRIPT1_ID;
import static school.hei.haapi.integration.conf.TestUtils.VERSION1_ID;
import static school.hei.haapi.integration.conf.TestUtils.STUDENT1_ID;
import static school.hei.haapi.integration.conf.TestUtils.anAvailableRandomPort;

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
    void get_transcript_raw_ok() throws ApiException, IOException {
        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
        TranscriptApi api = new TranscriptApi(student1Client);

        // Mocking the API response, replace the following lines with your actual logic to fetch the PDF from the API
        File pdfFile = api.getStudentTranscriptVersionPdf(STUDENT1_ID, TRANSCRIPT1_ID, VERSION1_ID);

        assertNotNull(pdfFile);
        // Add any other assertions as needed for the content of the PDF.
    }

    static class ContextInitializer extends AbstractContextInitializer {
        public static final int SERVER_PORT = anAvailableRandomPort();

        @Override
        public int getServerPort() {
            return SERVER_PORT;
        }
    }
}



//package school.hei.haapi.integration;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ContextConfiguration;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import school.hei.haapi.SentryConf;
//import school.hei.haapi.endpoint.rest.api.TranscriptApi;
//import school.hei.haapi.endpoint.rest.client.ApiClient;
//import school.hei.haapi.endpoint.rest.model.StudentTranscriptVersion;
//import school.hei.haapi.endpoint.rest.security.cognito.CognitoComponent;
//import school.hei.haapi.integration.conf.AbstractContextInitializer;
//import school.hei.haapi.integration.conf.TestUtils;
//
//import school.hei.haapi.endpoint.rest.client.ApiClient;
//import school.hei.haapi.endpoint.rest.client.ApiException;
//import school.hei.haapi.endpoint.rest.model.Course;
//import school.hei.haapi.endpoint.rest.model.CourseDirection;
//
//
//import java.io.File;
//import java.io.IOException;
//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
//import static school.hei.haapi.integration.conf.TestUtils.STUDENT1_ID;
//import static school.hei.haapi.integration.conf.TestUtils.STUDENT1_TOKEN;
//import static school.hei.haapi.integration.conf.TestUtils.STUDENT2_ID;
//import static school.hei.haapi.integration.conf.TestUtils.anAvailableRandomPort;
//import static school.hei.haapi.integration.conf.TestUtils.assertThrowsApiException;
//import static school.hei.haapi.integration.conf.TestUtils.setUpCognito;
//
//@SpringBootTest(webEnvironment = RANDOM_PORT)
//@Testcontainers
//@ContextConfiguration(initializers = TranscriptRawIT.ContextInitializer.class)
//@AutoConfigureMockMvc
//class TranscriptRawIT {
//    @MockBean
//    private SentryConf sentryConf;
//    @MockBean
//    private CognitoComponent cognitoComponentMock;
//
//    private static ApiClient anApiClient(String token) {
//        return TestUtils.anApiClient(token, TranscriptRawIT.ContextInitializer.SERVER_PORT);
//    }
//
//    @BeforeEach
//    void setUp() {
//        setUpCognito(cognitoComponentMock);
//    }
//
//    @Test
//    void read_ok() throws ApiException, IOException {
//        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
//        TranscriptApi api = new TranscriptApi(student1Client);
//
//        // Scenario 1: Student reads own transcript version
//        String studentId = STUDENT1_ID;
//        String transcriptId = "transcript_id_1";
//        String versionId = "version_id_1";
//        File versionPdfBytes = api.getStudentTranscriptVersionPdfWithHttpInfo(studentId, transcriptId, versionId).getData();
//
//        // Add your assertions for the response here
//        assertNotNull(versionPdfBytes);
//        assertTrue(versionPdfBytes.length() > 0);
//        // You can further assert specific content or metadata of the PDF if needed
//
//        // Scenario 2: Student reads another own transcript version
//        String transcriptId2 = "transcript_id_2";
//        String versionId2 = "version_id_2";
//        File versionPdfBytes2 = api.getStudentTranscriptVersionPdfWithHttpInfo(studentId, transcriptId2, versionId2).getData();
//
//        // Add your assertions for the response here
//        assertNotNull(versionPdfBytes2);
//        assertTrue(versionPdfBytes2.length() > 0);
//        // You can further assert specific content or metadata of the PDF if needed
//    }
//
//    @Test
//    void read_ko() {
//        ApiClient student1Client = anApiClient(STUDENT1_TOKEN);
//        TranscriptApi api = new TranscriptApi(student1Client);
//
//        // Scenario 1: Student tries to read another student's transcript version
//        String studentId = STUDENT1_ID;
//        String transcriptId = "transcript_id_1"; // Replace with a non-existing transcript ID
//        String versionId = "version_id_1"; // Replace with a non-existing version ID
//        assertThrowsApiException(
//                "{\"type\":\"403 FORBIDDEN\",\"message\":\"Access is denied\"}",
//                () -> api.getStudentTranscriptVersionPdfWithHttpInfo(STUDENT2_ID, transcriptId, versionId));
//
//        // Scenario 2: Student tries to read a non-existing transcript version
//        String nonExistingTranscriptId = "non_existing_transcript_id";
//        String nonExistingVersionId = "non_existing_version_id";
//        assertThrowsApiException(
//                "{\"type\":\"404 NOT_FOUND\",\"message\":\"Transcript version not found\"}",
//                () -> api.getStudentTranscriptVersionPdfWithHttpInfo(studentId, nonExistingTranscriptId, nonExistingVersionId));
//    }
//
//
//    static class ContextInitializer extends AbstractContextInitializer {
//        public static final int SERVER_PORT = anAvailableRandomPort();
//
//        @Override
//        public int getServerPort() {
//            return SERVER_PORT;
//        }
//    }
//}
