package org.testtask.demo.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.testtask.demo.application.entity.ImageEntity;
import org.testtask.demo.security.auth.dto.RegisterDTO;

import java.io.IOException;
import java.util.List;

public interface IImageService {
    public Page<ImageEntity> getImgList(Pageable pageable);

    public Page<ImageEntity> getImgList(Pageable pageable, String id);

    public ResponseEntity<byte[]> getImage(String imgName, String author) throws IOException;

    public ResponseEntity<String> addImages(String author, List<MultipartFile> images) throws IOException;

    public ResponseEntity<String> deleteImages(String author, String imgName) throws IOException;
}

