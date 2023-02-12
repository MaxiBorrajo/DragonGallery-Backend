/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DragonGallery.app.service;

import com.DragonGallery.app.model.Folder;
import com.DragonGallery.app.model.FileEntity;
import jakarta.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.DragonGallery.app.repository.FolderRepository;

/**
 *
 * @author Maximiliano Borrajo
 */
@Service
@Transactional
public class FolderService {
    @Autowired
    FolderRepository carpetaRepository;
    
    public List<Folder> getListOfFolders(){
        return carpetaRepository.findAll();
    }
    
    public List<FileEntity> getFilesOfFolderById(int id){
        Folder carpeta = carpetaRepository.findById(id).orElse(null);
        return carpeta.getFiles();
    }
    
    public List<FileEntity> getFilesOfFolderByName(String name){
        Folder carpeta = carpetaRepository.findByName(name);
        return carpeta.getFiles();
    }
    
    public Optional<Folder> getFolderById(int id){
        return carpetaRepository.findById(id);
    }
    
    public Folder getFolderByName(String name){
        return carpetaRepository.findByName(name);
    }
    
    public void saveFolder(Folder folder){
        carpetaRepository.save(folder);
    }
    
    public void delete(int id){
        carpetaRepository.deleteById(id);
    }
    
    public void deleteByName(String name){
        Folder carpeta = carpetaRepository.findByName(name);
        carpetaRepository.deleteById(carpeta.getId());
    }
}
