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
package mx.dr.forms.zul;

import java.util.List;

/**
 *
 * </br>
 * clase que sirve para limitar el numero de elementos en el modelo.
 * @version 1.0
 * @author Jorge Luis Martinez Ramirez
 * @since 13/08/2011
 * @since v0.5
 */
public class DRLimitedListModel extends DRGenericListModel{
    /**
	* maximo de elementos en el modelo.
	**/
    private int maxSize;
	/**
	 * @param maximo de elementos en el modelo.
	 **/
    public DRLimitedListModel(List results, int maxSize){
        super(results);
        this.maxSize= maxSize;
    }
	/**
     * getter
	 * @return maximo de elementos en el modelo.
	 **/
    public int getMaxSize() {
        return maxSize;
    }
    

}
