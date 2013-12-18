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

import java.util.HashMap;
import java.util.Map;

/**
 *
 * </br>
 * clase que encapsula multiples parametros de entrada para un servicio.
 * @version 1.0
 * @author Jorge Luis Martinez Ramirez
 * @since 13/08/2012
 */
public class GenericDtoParamsIN {
    /**
	* mapa de parametros que se pasan como entrada al servicio de negocio.
	**/
    private Map params=new HashMap();
    /**
	* getter
	* @return mapa de parametros que se pasan como entrada al servicio de negocio.
	**/
    public Map getParams() {
        return params;
    }
    /**
	* Obtiene el valor de un parametro basado en su identificador.
	* @param key idebrificador del parametro que se desea obtener.
	* @return valor del parametro deseado.
	**/
    public Object get(Object key){
        return params.get(key);
    }
}
