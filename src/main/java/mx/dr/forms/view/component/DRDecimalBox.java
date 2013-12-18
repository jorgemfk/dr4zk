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
import mx.dr.forms.view.render.DRDecimalBoxRender;
/**
 *
 *<br/>
 * Anotacion que define el comportamiento de un componente decimalbox.
 * @author Jorge Luis Martinez Ramirez
 * @version 1.0
 * @since 13/08/2012
 * @since v0.5
 */ 
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@DRRender(itemRenderer=DRDecimalBoxRender.class)
public @interface DRDecimalBox{
    /**
	* maximo numero de caracteres permitidos.
	**/
    int maxlenght() default DefaultValues.NO_LIMIT;
	/**
	* formato del texto como numero por ejemplo <code>#,##0.00</code>
	**/
    String format();
	/**
	* determina si el campo es de solo lectura.
	**/
    boolean readOnly() default false;
}
