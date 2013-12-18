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
package mx.dr.forms.view.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import mx.dr.forms.view.DRRender;
import mx.dr.forms.view.render.DRGmapsRender;
/**
 *
 * <br/>
 * Anotacion que define el comportamiento de un componente Gmaps.
 * @author Jorge Luis Martinez Ramirez
 * @version 1.0
 * @since 13/08/2012
 * @since v0.5
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@DRRender(itemRenderer=DRGmapsRender.class)
public @interface DRGmaps {
    /**
	* anchura del mapa.
	**/
    String width();
	/**
	* altura del mapa.
	**/
    String height();
	/**
	* nombre del atributo del objeto vista cuyo valor sera asignado como latitud del mapa, este debe ser del tipo Double.
	**/
    String latitude();
	/**
	 * nombre del atributo del objeto vista cuyo valor sera asignado como coordenada longitud del mapa, este debe ser del tipo Double.
	 **/
    String longitude();
	/**
	* nombre del atributo del cual se obtendra el texto de la marca en el mapa.  
	**/
    String gmarkContent();
	/**
	* determina si muestra un control peque&ntilde;o
	*/
    boolean showSmallCtrl() default false;
	/**
	* determina si se muestra el control largo del mapa.
	**/
     boolean showLargeCtrl() default false;
}
