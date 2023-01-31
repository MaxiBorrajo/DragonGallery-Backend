/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DragonGallery.app.service;

import com.DragonGallery.app.model.Imagen;
import com.DragonGallery.app.repository.imgRepository;
import jakarta.transaction.Transactional;
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
public class ImgEntityService {
    @Autowired
    imgRepository imgRepository;
    
    public List<Imagen> getListOfImages(){
        return imgRepository.findByOrderById();
    }
    
    public Optional<Imagen> getImageById(int id){
        return imgRepository.findById(id);
    }
    
    public void saveImg(Imagen imagen){
        imgRepository.save(imagen);
    }
    
    public void delete(int id){
        imgRepository.deleteById(id);
    }
    
    public boolean existsImageById(int id){
        return imgRepository.existsById(id);
    }
}
