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


/**
 *
 * </br>
 * clase que encapsula la peticion de encontrar un registro basado en su identificador unico.
 * @version 1.0
 * @author Jorge Luis Martinez Ramirez
 * @since 13/08/2012
 */
public class GenericDtoOneIN {
    /**
	* clase del tipo de parametro pasado
	**/
    private Class aclass;
	/**
	* parametro valuado como el identificador de un registro.
	**/
    private Object param;

    //private Desktop desktop;
    
	/**
	 * getter
	 * @return clase del tipo de parametro pasado
	 **/
    public Class getAclass() {
        return aclass;
    }
    /**
	 * setter
	 * @param aclass clase del tipo de parametro pasado
	 **/
    public void setAclass(Class aclass) {
        this.aclass = aclass;
    }
    /**
	 * getter
	 * @return parametro valuado como el identificador de un registro.
	 **/
    public Object getParam() {
        return param;
    }
    /**
	 * setter
	 * @param param parametro valuado como el identificador de un registro.
	 **/
    public void setParam(Object param) {
        this.param = param;
    }
    /*
    public Desktop getDesktop() {
        return desktop;
    }

    public void setDesktop(Desktop desktop) {
        this.desktop = desktop;
    }*/


}
