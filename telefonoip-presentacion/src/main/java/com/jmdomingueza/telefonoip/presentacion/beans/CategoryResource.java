package com.jmdomingueza.telefonoip.presentacion.beans;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.Icon;

public interface CategoryResource extends Serializable{

	 /**
     * Metodo que devuelve el icono que corresponde con el nombre que pasamos
     * @param iconName nombre de la propiedad donde esta la url del icono.
     * @return
     */
    public  Icon getIcon(String iconName);
    
    
    /**
     * Metodo que devuelve la imagen que corresponde con el nombre que pasamos
     * @param imageName nombre de la propiedad donde esta la url de la imagen.
     * @return
     */
    public Image getImage(String imageName);
    
    /**
     * Metodo que devuelve el String de la propiedad que pasamos en la clave
     * @param key Clave del property
     * @return
     */
    public String getProperty(String key);
}
