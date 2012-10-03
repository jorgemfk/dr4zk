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
package mx.dr.forms.view.utils;

import java.lang.reflect.Field;
import java.util.Comparator;

import mx.dr.forms.constants.FormActions;
import mx.dr.forms.dto.DRFieldDto;
import mx.dr.forms.view.DRField;

/**
 *
 *<br/>
 * Comparador de atributos para ordenarlos de acuerdo a lo definido.
 * @author Jorge Luis Martinez Ramirez
 * @version 1.0
 * @since 13/08/2012
 * @since v0.5
 */
public class DRFieldComparator implements Comparator<DRFieldDto> {
    /**
	* accion a la que pertenecen los atributos.
	**/
    private FormActions action;
    /**
	* constructor por defecto.
	 * @param action  accion a la que pertenecen los atributos.
	**/
    public DRFieldComparator(FormActions action) {
        this.action = action;
    }
    /**
	* compara el orden introducidos por el usuario.
	* @see java.util.Comparator#compare
	**/
    public int compare(DRFieldDto d1, DRFieldDto d2) {
        DRField drfield1= d1.getDrField();
        DRField drfield2= d1.getDrField();
        Field o1 = d1.getField();
        Field o2 = d2.getField();
        o1.setAccessible(true);
        o2.setAccessible(true);
        return drfield1.order() - drfield2.order();
    }
}
