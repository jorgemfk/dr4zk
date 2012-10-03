/*
*
*
* Copyright (C) 2011-2012 Jorge Luis Martinez Ramirez
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as
* published by the Free Software Foundation, either version 3 of the
* License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
* Author: Jorge Luis Martinez Ramirez
* Email: jorgemfk1@gmail.com
*/
package mx.dr.forms.dto;

import org.zkoss.util.media.Media;

/**
 *
 *
 * </br>
 * Clase dto de soporte para la encapsulacion de los datos relacinados a un archivo cargado.
 * @version 1.0
 * @author Jorge Luis Martinez Ramirez
 * @since 13/08/2012
 */
public class DRMedia {
    /**
	* nombre del medio cargado.
	**/
    private String name;
	/**
	* contenido del archivo cargado.
	**/
    private Media media;
	/**
	* objeto que encapsula los datos originales del archivo cargado.
	**/
    private DRAttachMedia bo;
    /**
	* constructor por defecto.
	**/
    public DRMedia() {
    }
    /**
	*constructor
	* @param name nombre del archivo.
	**/
    public DRMedia(String name) {
        this.name = name;
    }

	/**
	 * getter
	 * @return objeto que encapsula los datos originales del archivo cargado.
	 * 
	 **/ 
    public DRAttachMedia getBo() {
        return bo;
    }
    /**
	 * setter
	 * @param bo objeto que encapsula los datos originales del archivo cargado.
	 * 
	 **/
    public void setBo(DRAttachMedia bo) {
        this.bo = bo;
    }
    /**
	* getter
	* @return contenido del archivo cargado.
	**/
    public Media getMedia() {
        return media;
    }
	/**
	 * setter
	 * @param media contenido del archivo cargado.
	 **/
    public void setMedia(Media media) {
        this.media = media;
    }
	/**
   	 * getter
	 * @return nombre del medio cargado.
	 **/ 
    public String getName() {
        return name;
    }
    /**
	 * setter
	 * @param name nombre del medio cargado.
	 **/
    public void setName(String name) {
        this.name = name;
    }

    /**
	* determina si el medio ha sido cargado.
	* @return verdadero si ha sido cargado, falso de otra manera.
	**/
    public boolean getUploaded() {
        return media != null || bo != null;
    }
    /**
	 * determina si el medio No ha sido cargado.
	 * @return verdadero si no ha sido cargado, falso de otra manera.
	 **/
    public boolean getNotUploaded() {
        return media == null && bo == null;
    }
    /**
	* determina si el medio ha sido recuperado.
	* @return verdadero si el medio es recuperado falso de otra manera.
	**/
    public boolean isAttachLoaded() {
        return bo != null;
    }
	/**
	* determina si el medio no ha sido cargado.
	* @return verdadero si no ha sido cargado, falso de otra manera.
	**/
    public boolean isNotAttachLoaded() {
        return bo == null;
    }
}
