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
package mx.dr.forms.dto;

import org.zkoss.zul.Row;

/**
 *
 * </br>
 * Clase que lleva el estado de la creacion de un <code>grid</code>.
 * @version 1.0
 * @author Jorge Luis Martinez Ramirez
 * @since 13/08/2012
 */
public class DRSupportViewDTO {
    /**
	* fila actual.
	**/
    private Row arow;
	/**
	* total de columnas generadas.
	**/
    private int cont;
    /**
	* constructor por defecto.
	* @param arow fila actual.
	* @param cont columnas totales.
	**/
    public DRSupportViewDTO(Row arow, int cont) {
        this.arow = arow;
        this.cont = cont;
    }
	/**
	* getter
	 * @return fila actual.
	 **/
    public Row getArow() {
        return arow;
    }
    /**
	 * setter
	 * @param arow fila actual.
	 **/	
    public void setArow(Row arow) {
        this.arow = arow;
    }
    /**
	* getter
	 * @return total de columnas generadas.
	 **/
    public int getCont() {
        return cont;
    }
	/**
	 * setter
	 * @param cont total de columnas generadas.
	 **/
    public void setCont(int cont) {
        this.cont = cont;
    }

    
}
