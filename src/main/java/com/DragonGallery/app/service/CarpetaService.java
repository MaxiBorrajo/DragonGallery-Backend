/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DragonGallery.app.service;

import com.DragonGallery.app.model.Carpeta;
import com.DragonGallery.app.model.Imagen;
import com.DragonGallery.app.repository.carpetaRepository;
import jakarta.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Maximiliano Borrajo
 */
@Service
@Transactional
public class CarpetaService {
    @Autowired
    carpetaRepository carpetaRepository;
    
    public List<Carpeta> getListOfFolders(){
        return carpetaRepository.findAll();
    }
    
    public List<Imagen> getImagesOfFolderById(int id){
        Carpeta carpeta = carpetaRepository.findById(id).orElse(null);
        return carpeta.getImagenes();
    }
    
    public List<Imagen> getImagesOfFolderByName(String name){
        Carpeta carpeta = carpetaRepository.findByName(name);
        return carpeta.getImagenes();
    }
    
    public Optional<Carpeta> getFolderById(int id){
        return carpetaRepository.findById(id);
    }
    
    public Carpeta getFolderByName(String name){
        return carpetaRepository.findByName(name);
    }
    
    public void saveFolder(Carpeta folder){
        carpetaRepository.save(folder);
    }
    
    public void delete(int id){
        carpetaRepository.deleteById(id);
    }
    
    public void deleteByName(String name){
        Carpeta carpeta = carpetaRepository.findByName(name);
        carpetaRepository.deleteById(carpeta.getId());
    }
}
