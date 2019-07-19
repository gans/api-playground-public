package com.hotmart.playground.category;

import com.hotmart.playground.conf.ApiProperties;
import com.hotmart.playground.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(
        path = "${spring.data.rest.basePath}/v1/categories",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class CategoryController {

    private final ApiProperties apiProperties;

    private final StorageService storageService;

    private final CategoryService categoryService;

    private final String basePath;

    public CategoryController(ApiProperties apiProperties,
                              StorageService storageService,
                              CategoryService categoryService,
                              @Value("${spring.data.rest.basePath}") String basePath) {

        this.storageService = storageService;
        this.categoryService = categoryService;
        this.apiProperties = apiProperties;
        this.basePath = basePath;
    }

    @GetMapping
    public Page<Category> findAll(final Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @PostMapping
    public Category create(@RequestParam("name") @NotEmpty final String name,
                           @RequestParam("description") @NotEmpty final String description,
                           @RequestParam("image") @NotNull final MultipartFile image) {

        String key = UUID.randomUUID().toString() + resolveFileExtension(image);
        storageService.store(key, image);

        String imageUrl = apiProperties.getBaseUrl() + basePath + "/v1/files/" + key;
        Category category = Category.builder()
                .name(name)
                .description(description)
                .image(imageUrl)
                .build();

        return categoryService.save(category);
    }

    private String resolveFileExtension(@RequestParam("image") final MultipartFile image) {
        String extension = "";
        if (StringUtils.hasText(image.getContentType())) {
            MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
            try {
                MimeType mimeType = allTypes.forName(image.getContentType());
                extension = mimeType.getExtension();
            } catch (MimeTypeException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return extension;
    }
}
