package com.hotmart.playground.storage;

import org.apache.tika.Tika;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping(
        path = "${spring.data.rest.basePath}/v1/files"
)
public class StorageController {

    private final Tika tika;

    private final StorageService storageService;

    public StorageController(Tika tika, StorageService storageService) {
        this.tika = tika;
        this.storageService = storageService;
    }

    @ResponseBody
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> download(@PathVariable @NotNull final String filename) {
        Resource file = storageService.loadAsResource(filename);
        String mimeType = tika.detect(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, Optional.ofNullable(mimeType).orElse("application/octet-stream"))
                .body(file);
    }
}
