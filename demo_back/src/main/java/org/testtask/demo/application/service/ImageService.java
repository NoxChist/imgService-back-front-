package org.testtask.demo.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.testtask.demo.application.entity.ImageEntity;
import org.testtask.demo.application.entity.ImageId;
import org.testtask.demo.application.repository.ImageRepository;
import org.testtask.demo.security.auth.service.IAuthenticationService;
import org.testtask.demo.security.auth.user.entitiy.ImageServiceUser;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.testtask.demo.utils.ImgServiceTools.transferFiles;

@RequiredArgsConstructor
@Service
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final IAuthenticationService authService;

    @Value("${image.base.folder}")
    protected String IMAGE_BASE_FOLDER;

    @Override
    public Page<ImageEntity> getImgList(Pageable pageable) {
        return imageRepository.findAll(pageable);
    }

    @Override
    public Page<ImageEntity> getImgList(Pageable pageable, String author) {
        return imageRepository.findAllByIdAuthor(author, pageable);
    }

    @Override
    public ResponseEntity<byte[]> getImage(String imgName, String author) throws IOException {
        File file = new File(String.format("%s\\%s\\%s",
                IMAGE_BASE_FOLDER, author, imgName));
        if (!file.exists()) {
            throw new FileNotFoundException("File not found...");
        }

        try (InputStream stream = new FileInputStream(file);
             BufferedInputStream bufferedStream = new BufferedInputStream(stream)) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bufferedStream.readAllBytes());
        }
    }

    @Override
    public ResponseEntity<String> addImages(String author, List<MultipartFile> images) throws IOException {
        File imageFolder = new File(String.format("%s\\%s", IMAGE_BASE_FOLDER, author));
        if(!imageFolder.exists()){
            imageFolder.mkdir();
        }
        List<String> addedImages = transferFiles(imageFolder, images);

        if(!addedImages.isEmpty()){
            var user = authService.getImageServiceUser(author);

            if(user!=null){

                for (String imgName : addedImages) {
                    ImageId imgId = new ImageId();
                    imgId.setAuthor(author);
                    imgId.setImgName(imgName);
                    var imageEntity = new ImageEntity(imgId, LocalDate.now(),user);
                    imageRepository.save(imageEntity);
                }
                return ResponseEntity.ok().body("Image added successfully");
            }
        }
        return ResponseEntity.badRequest().body("Something went wrong!:(");
    }

    @Override
    public ResponseEntity<String> deleteImages(String author, String imgName) throws IOException {
        File image = new File(String.format("%s\\%s\\%s", IMAGE_BASE_FOLDER, author, imgName));
        if(image.exists()){
            image.delete();
            var user = authService.getImageServiceUser(author);
            if(user!=null){
                ImageId imgId = new ImageId();
                imgId.setAuthor(author);
                imgId.setImgName(imgName);
                var imageEntity = new ImageEntity(imgId, LocalDate.now(),user);
                imageRepository.delete(imageEntity);
                return ResponseEntity.ok().body("Image deleted successfully");
            }else {
                File userFolder = new File(String.format("%s\\%s", IMAGE_BASE_FOLDER, author));
                var files = userFolder.listFiles();
                if(files!=null){
                    for (File file : files) {
                        file.delete();
                    }
                }
                userFolder.delete();
                return ResponseEntity.ok().body("Image deleted successfully");
            }
        } else {
            throw new FileNotFoundException();
        }
    }
}
