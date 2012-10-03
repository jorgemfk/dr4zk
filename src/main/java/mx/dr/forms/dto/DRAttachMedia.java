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

/**
 *
 * </br>
 * Interfase que determina los elementos minimos necesarios que debe tener una clase de objeto que se use para cargar archivos en la aplicacion.
 * @version 1.0
 * @author Jorge Luis Martinez Ramirez
 * @since 13/08/2012
 */
public interface DRAttachMedia {
/**
 * Es el getter del nombre original del del archivo.
 * @return original file name / nombre original del archivo.
 **/
public String getName();
/**
 * Es el getter de la extension del archivo.
 * @return format or file extention / formato o extension del archivo.
 **/
public String getFormat();
/**
 * Es el getter del tipo mime del archivo.
 * @return MimeType
 **/
public String getContentType();
/**
 * Es el getter de la uri de la obtencion del archivo.
 * @return Uri desde la cual se podra recuperar el archivo cuando ya ha sido cargado y guardado correctamente.
 **/
public String getUri();
}
