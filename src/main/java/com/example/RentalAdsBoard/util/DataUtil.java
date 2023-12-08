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
    public static String imageToBase64(String path) {
        String imageBase64;
        try {
            byte[] imageBytes = Files.readAllBytes(Paths.get(path));
            imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            return null;
        }
        return imageBase64;
    }

    public static String saveOrUpdateImage(String imageBase64, String originalPath, String path, boolean isAvatar) {
        String newPath = null;
        try {
            deleteImage(originalPath);

            String resourcesPath = System.getProperty("user.dir") + "\\src\\main\\resources\\" + path;

            if (imageBase64 == null || imageBase64.isEmpty()) {
                if (isAvatar) {
                    return resourcesPath + "\\" + "default.webp";
                } else return null;
            }
            else {
                String alterFileName = UUID.randomUUID().toString();
                newPath = resourcesPath + "\\" + alterFileName + ".png";
                byte[] imageBytes = Base64.getDecoder().decode(imageBase64);
                Files.write(Paths.get(newPath), imageBytes);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return newPath;
    }

    public static void deleteImage(String path) throws IOException {
        if (!(path == null || path.isEmpty()) && !path.contains("default")) {
            Files.deleteIfExists(Paths.get(path));
        }
    }

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
