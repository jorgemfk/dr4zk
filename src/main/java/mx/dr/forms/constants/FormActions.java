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
package mx.dr.forms.constants;

/**
 *
 * </br></br>
 * <code>enum</code> que define los tipos de comportamientos que se pueden manejar en la vista: crear, actualizar, leer, buscar, lista. 
 * @version 1.0
 * @author Jorge Luis Martinez Ramirez
 * @since 13/08/2012
 */
public enum FormActions {
    ADD,EDIT,READ,SEARCH,LIST;
	/**
	*
	* Metodo que encuentra el valor de la enumeracion para el nombre dado.
	* @param action - Nombre de la accion contenida en la enumeracion.
	* @return Valor de la enumeracion encontrada.
	**/
    public static FormActions getValue(String action){
        for(FormActions aenum : FormActions.values()){
            if(aenum.name().equals(action)){
                return aenum;
            }
        }
        return null;
    }
}
