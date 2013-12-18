/*
*
*
* Copyright (C) 2012 Jorge Luis Martinez Ramirez
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
package mx.dr.forms.view.validable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import mx.dr.forms.view.DRValidate;
import mx.dr.forms.view.validator.DRNotEmptyValidator;
/**
 *
 * <br/>
 * Anotacion que define el comportamiento de la validacion que compara que el valor del atributo anotado sea mayor o menor a otro valor de otro atributo referenciado (por el momento solo aplicable a fechas).
 * @author Jorge Luis Martinez Ramirez
 * @version 1.0
 * @since 13/08/2012
 * @since v0.5
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@DRValidate(itemValidate=DRNotEmptyValidator.class)
public @interface DRValidateReferenceDate{
    /**
	* enumeracion que define si el valor a comparar debe ser minimo o maximo
	**/
    public enum MODE{MAX,MIN}
	/**
	* atributo referenciado como punto de comparacion.
	**/
    String property();
	/**
	* el valor del atributo referenciado debe ser menor o mayor.
	**/
    MODE mode() default MODE.MIN;
    boolean applySearch() default false;
}
