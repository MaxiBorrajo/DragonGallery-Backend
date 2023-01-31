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
import java.lang.reflect.Array;
import java.util.List;
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

/**
 *
 * @author Maximiliano Borrajo
 */
@RestController
@RequestMapping("folder")
@CrossOrigin
public class carpetaController {
    @Autowired
    ImageService imgService;
    
    @Autowired
    ImgEntityService imgEntityService;
    
    @Autowired
    CarpetaService carpetaService;
    
    @GetMapping("allFolders")
    public List<Carpeta> getAllFolders(){
        return carpetaService.getListOfFolders();
    }
    
    @GetMapping("getFolder")
    public Carpeta getFolder(@RequestParam int id){
        return carpetaService.getFolderById(id).orElse(null);
    }
    
    @GetMapping("getFolder/{name}")
    public Carpeta getFolder(@PathVariable String name){
        return carpetaService.getFolderByName(name);
    }
    
    @GetMapping("getImagesOfFolder")
    public List<Imagen> getImagesOfFolderById(@RequestParam int id){
        return carpetaService.getImagesOfFolderById(id);
    }
    
    @GetMapping("getImagesOfFolder/{name}")
    public List<Imagen> getImagesOfFolderByName(@PathVariable String name){
        return carpetaService.getImagesOfFolderByName(name);
    }
    
    @PostMapping("create")
    public ResponseEntity<?> saveFolder(@RequestBody Carpeta carpeta){
        carpetaService.saveFolder(carpeta);
        return new ResponseEntity(new Message("Folder Created"), HttpStatus.OK);
    }
    
    @PostMapping("rename")
    public ResponseEntity<?> rename(@RequestBody Carpeta carpeta, @RequestParam String name){
        carpeta.setName(name);
        carpetaService.saveFolder(carpeta);
        return new ResponseEntity(new Message("Rename Uploaded"), HttpStatus.OK);
    }
    
    @DeleteMapping("delete")
    public ResponseEntity<?> deleteFolderById(@RequestParam int id){
        carpetaService.delete(id);
        return new ResponseEntity(new Message("Folder Deleted"), HttpStatus.OK);
    }
    
    @DeleteMapping("delete/{name}")
    public ResponseEntity<?> deleteFolderByName(@PathVariable String name){
        carpetaService.deleteByName(name);
        return new ResponseEntity(new Message("Folder Deleted"), HttpStatus.OK);
    }
}
