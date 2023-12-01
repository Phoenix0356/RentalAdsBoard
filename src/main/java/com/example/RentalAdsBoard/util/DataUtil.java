package com.example.RentalAdsBoard.util;


import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Component
public class DataUtil {
//    @Value("${avatar_storage.default}")
//    private static String defaultAvatar;
    public static String pictureToBase64(String path)  {
        String pictureBase64;
        try {
            byte[] pictureBytes = Files.readAllBytes(Paths.get(path));
            pictureBase64= "data:image/png;base64,"+Base64.getEncoder().encodeToString(pictureBytes);
        }catch (IOException e) {
            return null;
        }
        return pictureBase64;
    }

    public static String saveOrUpdateImage(String pictureBase64,String originalPath,String path,boolean isAvatar){
        String newPath=null;
        try {
            //delete the uploaded image
            deleteImage(originalPath);

            String resourcesPath = new ClassPathResource(path).getFile().getAbsolutePath();

            // if user don't upload an image
            if (pictureBase64 == null || pictureBase64.isEmpty()){
                if (isAvatar) {
                    return resourcesPath + "\\" + "default.jpg";
                } else return null;
            }
            // if user uploads an image
            else {
                //create save path and decode the Base64
                String alterFileName = UUID.randomUUID().toString();
                newPath = resourcesPath + "\\" + alterFileName + ".png";
                String base64WithoutPrefix = pictureBase64.replaceFirst("^data:image/\\w+;base64,", "");

                byte[] pictureBytes = Base64.getDecoder().decode(base64WithoutPrefix);
                //save the new image
                Files.write(Paths.get(newPath), pictureBytes);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return newPath;
    }

    //if there is already an uploaded picture,delete it.
    public static void deleteImage(String path) throws IOException {
        if (!(path == null || path.isEmpty()) && !path.contains("default")) {
            Files.deleteIfExists(Paths.get(path));
        }
    }



}
