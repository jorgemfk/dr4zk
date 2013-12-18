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
package mx.dr.forms.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import mx.dr.forms.view.component.DRLabel;
/**
 *
 * <br/>
 * Anotacion que define el comportamiento de un mensaje global.
 * @author Jorge Luis Martinez Ramirez
 * @version 1.0
 * @since 13/08/2012
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DRMessage {
    /**
	* enumeracion de las posibles apariencias a ser tratadas.
	*/
    public enum Mold{POPUP, LABEL}
	/**
	* etiqueta para el contenido del mensaje.
	* @return etiqueta del contenido del mensaje.
	**/
    DRLabel label() default @DRLabel(key="dr.forms.msg.label");
	/**
	* determina el modo en el que sera presentado un mensaje.
	* @return modo en el que sera presentado el mensaje.
	**/
    Mold mold() default Mold.POPUP;
	/**
	* clase css para el estilo del mensaje.
	**/
    String clazz() default "";
}
