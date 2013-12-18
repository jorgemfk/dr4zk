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

import mx.dr.forms.constants.DefaultValues;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import mx.dr.forms.view.DRRender;
import mx.dr.forms.view.render.DRTextBoxRender;
/**
 *
 * <br/>
 * Anotacion que define el comportamiento de un componente textbox
 * @author Jorge Luis Martinez Ramirez
 * @version 1.0
 * @since 13/08/2012
 * @since v0.5
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@DRRender(itemRenderer=DRTextBoxRender.class)
public @interface DRTextBox{
    /**
	* tipo de campo de texto.
	**/
    public enum TxtType{text, password}
	/**
	* maximo de caracteres permisibles en el campo de texto.
	**/
    int maxlenght() default DefaultValues.NO_LIMIT;
	/**
	* numero de columnas a mostrar en el campo de texto.
	**/
    int cols() default 40;
	/**
	* define si es de solo lectura.
	*/
    boolean readOnly() default false;
	/**
	* nunero de filas en el campo de texto.
	**/
    int rows() default DefaultValues.ONE_ROW;
	/**
	* tipo que define como se va a mostrar el texto.
	**/
    TxtType type() default TxtType .text;
	/**
	* define si el texto introducido sera transformado a mayusculas.
	**/
    boolean uppercase() default false;
	/**
	* define el formato del texto a itroducir. requiere js lib jquery jmask.
	**/
    String mask() default DefaultValues.NONE;
}
