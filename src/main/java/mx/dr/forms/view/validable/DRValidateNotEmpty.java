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
import mx.dr.forms.constants.DefaultValues;
import mx.dr.forms.view.DRValidate;
import mx.dr.forms.view.validator.DRNotEmptyValidator;
/**
 *
 *<br/>
 * Anotacion que define el comportamiento de la validacion que indica que un campo es obligatorio en su captura por el usuario.
 * @author Jorge Luis Martinez Ramirez
 * @version 1.0
 * @since 13/08/2012
 * @since v0.5
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@DRValidate(itemValidate=DRNotEmptyValidator.class)
public @interface DRValidateNotEmpty{
    /**
	* si se desea verificar el valor de un atributo  encapsulado en el objeto valor anotado (ejemplo valor seleccionado combobox o listbox)
	**/
    String property() default DefaultValues.NONE;
	/**
	* el valor del atributo definido en <code>property</code> que se toma como invalido, en combobox seria por ejemplo el valor de la opcion <code>seleccione una opcion</code>
	**/
    String emptyValue() default "-1";
    boolean applySearch() default false;
}
