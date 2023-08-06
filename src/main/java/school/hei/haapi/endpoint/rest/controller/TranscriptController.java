package school.hei.haapi.endpoint.rest.controller;

//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import com.amazonaws.services.s3.model.S3Object;
//import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students/{student_id}/transcripts/{transcript_id}/versions/{version_id}/raw")
public class TranscriptController {

//        @GetMapping
//        public ResponseEntity<InputStreamResource> getStudentTranscriptVersionPdf(
//                @PathVariable("student_id") String studentId,
//                @PathVariable("transcript_id") String transcriptId,
//                @PathVariable("version_id") String versionId
//        ) {
//            final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
//            String bucketName = "YOUR_BUCKET_NAME"; // Remplacez par le nom de votre bucket S3
//            String documentKey = "YOUR_DOCUMENT_KEY"; // Remplacez par la clé (chemin) de votre document PDF dans le bucket S3
//
//            S3Object object = s3.getObject(bucketName, documentKey);
//            S3ObjectInputStream s3is = object.getObjectContent();
//
//            return ResponseEntity.ok()
//                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
//                    .cacheControl(CacheControl.noCache())
//                    .header("Content-Disposition", "attachment; filename=" + "transcript.pdf")
//                    .body(new InputStreamResource(s3is));
//        }
}


