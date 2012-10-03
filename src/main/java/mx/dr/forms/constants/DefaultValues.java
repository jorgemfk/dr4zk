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
 * Clase que define los valores por defecto utilizados en las anotaciones de componentes.
 * @version 1.0
 * @author Jorge Luis Martinez Ramirez
 * @since 13/08/2011
 * @since v0.5
 */
public class DefaultValues {
    /**
	* </br></br>
	* Costructor que no permite crear instancias.
	**/
    private DefaultValues(){}
	/**
    * </br></br>
	* Valor por defecto para las entradas tipo <code>String</code>.
	**/
    public static final String NONE="";
	/**
	* </br></br>
	* Valor que indica el no limite de un valor entero.
	**/
    public static final int NO_LIMIT = -1;
	/**
	* </br></br>
	* Valor que indica el valor del numero de filas por defecto.
	**/
    public static final int ONE_ROW = 1;
	/**
	* </br></br>
	* Valor que indica el fotmato por defecto de las fechas.
	**/
    public static final String DATE_FORMAT = "dd/MM/yyyy";

}
