package org.testtask.demo.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImgServiceTools {
    public static List<String> transferFiles(File imageFolder, List<MultipartFile> images) throws IOException {
        var addedImgs = new ArrayList<String>();

        if (imageFolder.exists() && images != null) {
            for (MultipartFile image : images) {
                if (image != null && !images.isEmpty()) {
                    String imgName = image.getOriginalFilename();
                    if (!StringUtils.endsWithIgnoreCase(imgName, ".jpg") &&
                            !StringUtils.endsWithIgnoreCase(imgName, ".jpeg") &&
                            !StringUtils.endsWithIgnoreCase(imgName, ".png"))  {
                        throw new FileNotFoundException("Image file must be JPG/PNG format");
                    }
                    File newImg = new File(String.format("%s\\%s", imageFolder, imgName));
                    image.transferTo(newImg);
                    if(newImg.exists()){
                        addedImgs.add(imgName);
                    }
                } else {
                    throw new NullPointerException("Image is null or empty...");
                }
            } return addedImgs;
        } else {
            throw new IOException("Failed to transfer files...");
        }
    }
}
