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
import java.io.IOException;
import java.util.List;
import java.util.Map;
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
@CrossOrigin("*")
public class FileController {

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    FileService fileService;

    @Autowired
    FolderService folderService;

    @GetMapping("getFiles")
    public ResponseEntity<List<FileEntity>> getFile() {
        return new ResponseEntity(fileService.getListOfFiles(), HttpStatus.OK);
    }

    @GetMapping("getFile/{id}")
    public ResponseEntity<FileEntity> getFileById(@PathVariable int id) {
        return new ResponseEntity(fileService.getFileById(id), HttpStatus.OK);
    }

    @GetMapping("getDetails/{id}")
    public Map getDetailsById(@PathVariable int id) throws Exception {
        FileEntity file = fileService.getFileById(id).orElse(null);
        return cloudinaryService.details(file);
    }

    @PostMapping("upload")
    public ResponseEntity<?> upload(@RequestParam MultipartFile multipartFile, @RequestParam(defaultValue = "allPhotos") String folder_name) throws IOException, Exception {
        if (multipartFile.getContentType().startsWith("image/")) {
            Map result = cloudinaryService.uploadImage(multipartFile);
            FileEntity file = new FileEntity();
            file.setName((String) result.get("original_filename"));
            file.setUrl((String) result.get("url"));
            file.setCloud_id((String) result.get("public_id"));
            file.setFormat("image");
            Folder folder = folderService.getFolderByName(folder_name);
            if (folder == null) {
                Folder newFolder = new Folder();
                newFolder.setName("allPhotos");
                folderService.saveFolder(newFolder);
                file.setCarpeta(newFolder);
                fileService.saveFile(file);
                return new ResponseEntity(new Message("File Uploaded"), HttpStatus.OK);
            }else{
                file.setCarpeta(folder);
                fileService.saveFile(file);
                return new ResponseEntity(new Message("File Uploaded"), HttpStatus.OK);
            }
        } else if (multipartFile.getContentType().startsWith("video/")) {
            Map result = cloudinaryService.uploadVideo(multipartFile);
            FileEntity file = new FileEntity();
            file.setName((String) result.get("original_filename"));
            file.setUrl((String) result.get("url"));
            file.setCloud_id((String) result.get("public_id"));
            file.setFormat("video");
            Folder folder = folderService.getFolderByName(folder_name);
            file.setCarpeta(folder);
            fileService.saveFile(file);
            return new ResponseEntity(new Message("File Uploaded"), HttpStatus.OK);
        } else {
            return new ResponseEntity(new Message("Invalid file, it must be an image or a video"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("rename")
    public ResponseEntity<?> rename(@RequestBody FileEntity file, @RequestParam String name) {
        file.setName(name);
        fileService.saveFile(file);
        return new ResponseEntity(new Message("Rename Uploaded"), HttpStatus.OK);
    }

    @PostMapping("move")
    public ResponseEntity<?> move(@RequestBody FileEntity file, @RequestParam int id_folder) {
        Folder carpeta = folderService.getFolderById(id_folder).orElse(null);
        file.setCarpeta(carpeta);
        fileService.saveFile(file);
        return new ResponseEntity(new Message("Moved Succesfully"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) throws IOException {
        if (fileService.existsFileById(id)) {
            FileEntity file = fileService.getFileById(id).orElse(null);
            Map result = cloudinaryService.delete(file);
            fileService.delete(id);
            return new ResponseEntity(new Message("File Deleted"), HttpStatus.OK);
        } else {
            return new ResponseEntity(new Message("File Not Found"), HttpStatus.NOT_FOUND);
        }
    }
}
