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
package mx.dr.forms.dto;

import java.lang.reflect.Field;
import mx.dr.forms.view.DRField;

/**
 *
 * </br>
 * Clase dto que encapsula la definicion de las acciones ligadas a un atributo de vista.
 * @version 1.0
 * @author Jorge Luis Martinez Ramirez
 * @since 13/08/2012
 */
public class DRFieldDto {
    /**
	* atributo de la clase modelo de vista.
	**/
    private Field field;
	/**
	* anotacion que define al comportamiento de accion del atributo en la vista.
	**/
    private DRField drField;
    /**
	* contructor por defecto.
	* @param field atributo de la clase de vista
	* @param drField anotacion de comportamiento de accion para la vista.
	**/
    public DRFieldDto(Field field, DRField drField) {
        this.field = field;
        this.drField = drField;
    }
    /**
	 * getter
	 * @return anotacion que define al comportamiento de accion del atributo en la vista.
	 **/
    public DRField getDrField() {
        return drField;
    }
    /**
	 * getter
	 * @return atributo de la clase modelo de vista.
	 **/
    public Field getField() {
        return field;
    }
    
}
