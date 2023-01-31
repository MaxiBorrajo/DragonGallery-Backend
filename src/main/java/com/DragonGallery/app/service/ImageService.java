/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DragonGallery.app.service;

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
public class ImageService {

    Cloudinary cloudinary;

    public ImageService() {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dxbixgv6n",
                "api_key", "213687998788369",
                "api_secret", "GZfC36uYWBVKm5WMw0PhRIIQoD4"));
    }

    public Map upload(MultipartFile multipartFile) throws IOException, Exception {
        File file = convert(multipartFile);
        Map params1 = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", true);
        Map result = cloudinary.uploader().upload(file, params1);
        file.delete();
        return result;
    }

    public Map delete(String id) throws IOException {
        Map result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        return result;
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }

    public Map details(String public_id) throws Exception {
        Map result = cloudinary.api().resource(public_id, ObjectUtils.asMap("versions", true));
        return result;
    }
}
