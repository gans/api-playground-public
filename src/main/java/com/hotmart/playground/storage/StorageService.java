package com.hotmart.playground.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    void init();

    void store(String key, MultipartFile file);

    Path load(String filename);

    Resource loadAsResource(String filename);

}
