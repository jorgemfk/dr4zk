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
/**
 *
 * <br/>
 * Anotacion que define la clase a la cual sera transformado el objeto vista, generalmente se trata de una clase de negocio que puede ser persistida en una base de datos.
 * @author Jorge Luis Martinez Ramirez
 * @version 1.0
 * @since 13/08/2012
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DRRootEntity {
    /**
     *
     * <br/>
     * define la clase a la cual sera transformado el objeto vista, generalmente se trata de una clase de negocio que puede ser persistida en una base de datos.
    * @return clase de negocio a la que se transformara el objeto vista.
	**/
    Class entity();
}
