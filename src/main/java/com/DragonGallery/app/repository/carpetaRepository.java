/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.DragonGallery.app.repository;

import com.DragonGallery.app.model.Carpeta;
import com.DragonGallery.app.model.Imagen;
import java.lang.reflect.Array;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Maximiliano Borrajo
 */
@Repository
public interface carpetaRepository extends JpaRepository<Carpeta, Integer>{
    public Carpeta findByName(String name);
}
