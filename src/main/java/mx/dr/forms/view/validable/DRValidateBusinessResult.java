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
import mx.dr.forms.view.validator.DRBusinessResultValidator;
/**
 *
 * <br/>
 * Anotacion que define el comportamiento de la validacion que utiiza que el valor del atributo anotado que es pasado como parametro de entrada a un metodo de negocio que lo valida.
 * @author Jorge Luis Martinez Ramirez
 * @version 1.0
 * @since 13/08/2012
 * @since v0.5
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@DRValidate(itemValidate=DRBusinessResultValidator.class)
public @interface DRValidateBusinessResult{
    /**
	* define la accion que realizara la operacion de negocio para determinar si el valor introducido es valido, el valor devuelto debera ser un resultado booleanocon valor verdadero si la validacion se cumple y falso si no se cumple,  <code>package.Facade@method</code>
	**/
    String action();
    boolean applySearch() default false;
}
