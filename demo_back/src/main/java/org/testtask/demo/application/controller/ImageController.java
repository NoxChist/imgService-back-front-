package org.testtask.demo.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.testtask.demo.application.entity.ImageEntity;
import org.testtask.demo.application.service.IImageService;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/insta")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")

public class ImageController {
    private final IImageService imageService;
    private final String defaultSortColm = "publicationDate";
    private final String defaultSortDir = "desc";
    private final String defaultSize = "10";

    @GetMapping("/album")
    public Page<ImageEntity> getImgList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = defaultSize) int size,
            @RequestParam(defaultValue = defaultSortColm) String sortBy,
            @RequestParam(defaultValue = defaultSortDir) String sortDir) {

        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return imageService.getImgList(pageable);
    }

    @GetMapping("/album/{email}")
    public Page<ImageEntity> getImgList(
            @PathVariable String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = defaultSize) int size,
            @RequestParam(defaultValue = defaultSortColm) String sortBy,
            @RequestParam(defaultValue = defaultSortDir) String sortDir) {

        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return imageService.getImgList(pageable, email);
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage(
            @RequestParam(name = "author") String author,
            @RequestParam(name = "imgName") String imgName
            ) throws IOException {

        return imageService.getImage(imgName, author);

    }

    @PostMapping("/album/{email}")
    public ResponseEntity<String> addImg(
            @RequestPart("images") List<MultipartFile> images,
            @PathVariable String email) throws IOException {
        return imageService.addImages(email, images);
    }

    @DeleteMapping("/image")
    public ResponseEntity<String> deleteImage(
            @RequestParam(name = "author") String author,
            @RequestParam(name = "imgName") String imgName) throws IOException {
        System.out.println();
        return imageService.deleteImages(author, imgName);
    }
}
