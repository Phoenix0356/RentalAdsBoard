package com.example.RentalAdsBoard.util;


import com.example.RentalAdsBoard.entity.BaseEntity;
import com.example.RentalAdsBoard.entity.Image;
import com.example.RentalAdsBoard.entity.User;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Component
public class DataUtil {
    //    @Value("${avatar_storage.default}")
//    private String defaultAvatar;
    public static String imageToBase64(String path) {
        String imageBase64;
        try {
            byte[] imageBytes = Files.readAllBytes(Paths.get(path));
            imageBase64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            return null;
        }
        return imageBase64;
    }

    public static String saveOrUpdateImage(String imageBase64, String originalPath, String path, boolean isAvatar) {
        String newPath = null;
        try {
            //delete the uploaded image
            deleteImage(originalPath);

            //String resourcesPath = new ClassPathResource(path).getFile().getAbsolutePath();
            String resourcesPath = System.getProperty("user.dir") + "\\src\\main\\resources\\" + path;

            // if user don't upload an image
            if (imageBase64 == null || imageBase64.isEmpty()) {
                if (isAvatar) {
                    return resourcesPath + "\\" + "default.webp";
                } else return null;
            }
            // if user uploads an image
            else {
                //create save path and decode the Base64
                String alterFileName = UUID.randomUUID().toString();
                newPath = resourcesPath + "\\" + alterFileName + ".png";
                String base64WithoutPrefix = imageBase64.replaceFirst("^data:image/\\w+;base64,", "");
                byte[] imageBytes = Base64.getDecoder().decode(base64WithoutPrefix);
                //save the new image
                Files.write(Paths.get(newPath), imageBytes);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return newPath;
    }

    //if there is already an uploaded image,delete it.
    public static void deleteImage(String path) throws IOException {
        if (!(path == null || path.isEmpty()) && !path.contains("default")) {
            Files.deleteIfExists(Paths.get(path));
        }
    }

    //Delete all images using level order traversal
    public static void deleteAllImages(BaseEntity<?> entity) throws IOException {
        Queue<BaseEntity<?>> queue = new LinkedList<>();
        queue.offer(entity);
        while (!queue.isEmpty()) {
            BaseEntity<?> baseEntity = queue.poll();
            List<?> list = baseEntity.getList();

            if (baseEntity instanceof User) {
                deleteImage(((User) baseEntity).getAvatarPath());
            }
            if (baseEntity instanceof Image) {
                deleteImage(((Image) baseEntity).getPath());
            }

            if (list != null && !list.isEmpty()) {
                for (Object object : list) {
                    queue.offer((BaseEntity<?>) object);
                }
            }
        }
    }
}
