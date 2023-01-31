/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DragonGallery.app.dto;

import lombok.Data;

/**
 *
 * @author Maximiliano Borrajo
 */
@Data
public class Message {
    String message;

    public Message(String message) {
        this.message = message;
    }
}
