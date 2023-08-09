package school.hei.haapi.endpoint.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.haapi.endpoint.rest.mapper.TranscriptMapper;
import school.hei.haapi.endpoint.rest.model.Transcript;
import school.hei.haapi.service.TranscriptService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
public class TranscriptController {
    private final TranscriptService service;
    private final TranscriptMapper mapper;


    @PutMapping("/students/{id}/transcripts")
    public List<Transcript> crupdateTranscripts(@PathVariable("id") String studentId,
                                                @RequestBody List<Transcript> transcripts) {
        var toSave = transcripts.stream()
                .map(mapper::toDomain)
                .collect(Collectors.toUnmodifiableList());
        return service.crupdateTranscripts(toSave).stream()
                .map(mapper::toRest)
                .collect(Collectors.toList());
    }

}
