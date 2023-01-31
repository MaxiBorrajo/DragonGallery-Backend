/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DragonGallery.app.controller;

import com.DragonGallery.app.dto.Message;
import com.DragonGallery.app.model.Carpeta;
import com.DragonGallery.app.model.Imagen;
import com.DragonGallery.app.service.CarpetaService;
import com.DragonGallery.app.service.ImageService;
import com.DragonGallery.app.service.ImgEntityService;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Maximiliano Borrajo
 */
@RestController
@RequestMapping("img")
@CrossOrigin
public class imageController {

    @Autowired
    ImageService imgService;

    @Autowired
    ImgEntityService imgEntityService;

    @Autowired
    CarpetaService carpetaService;

    @GetMapping("getImages")
    public ResponseEntity<List<Imagen>> getImages() {
        return new ResponseEntity(imgEntityService.getListOfImages(), HttpStatus.OK);
    }

    @GetMapping("getImage/{id}")
    public ResponseEntity<Imagen> getImageById(@PathVariable int id) {
        return new ResponseEntity(imgEntityService.getImageById(id), HttpStatus.OK);
    }

    @GetMapping("getDetails/{id}")
    public Map getDetailsById(@PathVariable int id) throws Exception {
        Imagen imagen = imgEntityService.getImageById(id).orElse(null);
        return imgService.details(imagen.getCloud_id());
    }

    @PostMapping("upload")
    public ResponseEntity<?> upload(@RequestParam MultipartFile multipartFile, @RequestParam(defaultValue = "allPhotos") String folder_name) throws IOException, Exception {
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if (bi == null) {
            return new ResponseEntity(new Message("Invalid Image"), HttpStatus.BAD_REQUEST);
        }
        Map result = imgService.upload(multipartFile);
        Imagen img = new Imagen();
        img.setName((String) result.get("original_filename"));
        img.setUrl((String) result.get("url"));
        img.setCloud_id((String) result.get("public_id"));
        Carpeta carpeta = carpetaService.getFolderByName(folder_name);
        img.setCarpeta(carpeta);
        imgEntityService.saveImg(img);
        return new ResponseEntity(new Message("Image Uploaded"), HttpStatus.OK);
    }

    @PostMapping("rename")
    public ResponseEntity<?> rename(@RequestBody Imagen image, @RequestParam String name) {
        image.setName(name);
        imgEntityService.saveImg(image);
        return new ResponseEntity(new Message("Rename Uploaded"), HttpStatus.OK);
    }

    @PostMapping("move")
    public ResponseEntity<?> move(@RequestBody Imagen image, @RequestParam int id_folder) {
        Carpeta carpeta = carpetaService.getFolderById(id_folder).orElse(null);
        image.setCarpeta(carpeta);
        imgEntityService.saveImg(image);
        return new ResponseEntity(new Message("Moved Succesfully"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) throws IOException {
        if (imgEntityService.existsImageById(id)) {
            Imagen img = imgEntityService.getImageById(id).orElse(null);
            Map result = imgService.delete(img.getCloud_id());
            imgEntityService.delete(id);
            return new ResponseEntity(new Message("Image Deleted"), HttpStatus.OK);
        }
        return new ResponseEntity(new Message("Image Not Found"), HttpStatus.NOT_FOUND);
    }
}
