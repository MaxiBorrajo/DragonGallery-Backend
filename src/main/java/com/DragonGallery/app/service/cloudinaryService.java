/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DragonGallery.app.service;

import com.DragonGallery.app.model.FileEntity;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Maximiliano Borrajo
 */
@Service
public class CloudinaryService {

    Cloudinary cloudinary;

    public CloudinaryService() {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dxbixgv6n",
                "api_key", "213687998788369",
                "api_secret", "GZfC36uYWBVKm5WMw0PhRIIQoD4"));
    }

    public Map uploadImage(MultipartFile multipartFile) throws IOException, Exception {
        File file = convert(multipartFile);
        Map params1 = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", true);
        Map result = cloudinary.uploader().upload(file, params1);
        file.delete();
        return result;
    }

    public Map uploadVideo(MultipartFile multipartFile) throws IOException, Exception {
        File file = convert(multipartFile);
        Map params1 = ObjectUtils.asMap(
                "resource_type", "video",
                "use_filename", true,
                "unique_filename", true);
        Map result = cloudinary.uploader().upload(file, params1);
        file.delete();
        return result;
    }

    public Map delete(FileEntity file) throws IOException {
        if (file.getFormat().equals("video")) {
            Map result = cloudinary.uploader().destroy(file.getCloud_id(), ObjectUtils.asMap("resource_type", "video"));
            return result;
        }else{
            Map result = cloudinary.uploader().destroy( file.getCloud_id(), ObjectUtils.emptyMap());
            return result;
        }
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }

    public Map details(FileEntity file) throws Exception {
        if (file.getFormat().equals("video")) {
            Map result = cloudinary.api().resource(file.getCloud_id(),
                    ObjectUtils.asMap("versions", true, "resource_type", "video"));
            return result;
        }else{
            Map result = cloudinary.api().resource(file.getCloud_id(),
                    ObjectUtils.asMap("versions", true));
            return result;
        }
    }
}
