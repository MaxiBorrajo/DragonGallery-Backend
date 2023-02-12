/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DragonGallery.app.service;

import com.DragonGallery.app.model.FileEntity;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.DragonGallery.app.repository.FileRepository;

/**
 *
 * @author Maximiliano Borrajo
 */
@Service
@Transactional
public class FileService {
    @Autowired
    FileRepository fileRepo;
    
    public List<FileEntity> getListOfFiles(){
        return fileRepo.findByOrderById();
    }
    
    public Optional<FileEntity> getFileById(int id){
        return fileRepo.findById(id);
    }
    
    public void saveFile(FileEntity file){
        fileRepo.save(file);
    }
    
    public void delete(int id){
        fileRepo.deleteById(id);
    }
    
    public boolean existsFileById(int id){
        return fileRepo.existsById(id);
    }
}
