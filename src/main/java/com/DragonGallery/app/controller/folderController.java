/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DragonGallery.app.controller;

import com.DragonGallery.app.dto.Message;
import com.DragonGallery.app.model.Folder;
import com.DragonGallery.app.model.FileEntity;
import com.DragonGallery.app.service.FolderService;
import com.DragonGallery.app.service.CloudinaryService;
import com.DragonGallery.app.service.FileService;
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
public class FolderController {
    
    @Autowired
    FolderService carpetaService;
    
    @GetMapping("allFolders")
    public List<Folder> getAllFolders(){
        return carpetaService.getListOfFolders();
    }
    
    @GetMapping("getFolder")
    public Folder getFolder(@RequestParam int id){
        return carpetaService.getFolderById(id).orElse(null);
    }
    
    @GetMapping("getFolder/{name}")
    public Folder getFolder(@PathVariable String name){
        return carpetaService.getFolderByName(name);
    }
    
    @GetMapping("getFilesOfFolder")
    public List<FileEntity> getFilesOfFolderById(@RequestParam int id){
        return carpetaService.getFilesOfFolderById(id);
    }
    
    @GetMapping("getFilesOfFolder/{name}")
    public List<FileEntity> getFilesOfFolderByName(@PathVariable String name){
        return carpetaService.getFilesOfFolderByName(name);
    }
    
    @PostMapping("create")
    public ResponseEntity<?> saveFolder(@RequestBody Folder carpeta){
        carpetaService.saveFolder(carpeta);
        return new ResponseEntity(new Message("Folder Created"), HttpStatus.OK);
    }
    
    @PostMapping("rename")
    public ResponseEntity<?> rename(@RequestBody Folder carpeta, @RequestParam String name){
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
