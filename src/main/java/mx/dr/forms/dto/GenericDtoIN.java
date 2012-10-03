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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.dr.util.Base;

/**
 *
 * </br>
 * clase que encapsula el resultado de aplicar el metodo generico <code>doSomething</code> este debe ser el tipo de entrada a la fachada de servicio de negocio. 
 * @version 1.0
 * @author Jorge Luis Martinez Ramirez
 * @since 13/08/2012
 */
public class GenericDtoIN extends Base{
    /**
	*
	* instancia del modelo de vista con sus atributos valuados con lo introducido por el usuario.
	**/
	private Object viewDTO;
	/**
	* 
	* lista de objetos de negocio traducidos de la valuacion del objeto de vista.
	**/
    private List bos= new ArrayList();
	/**
	* 
	* parametros tomados de la sesion web.
	**/
    private Map<String,Object> sessionParams;
    /**
	 * getter
	 * @return parametros tomados de la sesion web.
	 **/
    public Map<String, Object> getSessionParams() {
        return sessionParams;
    }
    /**
	 * setter
	 * @param sessionParams parametros tomados de la sesion web.
	 **/
    public void setSessionParams(Map<String, Object> sessionParams) {
        this.sessionParams = sessionParams;
    }
    
    /**
	 * getter
	 * @return lista de objetos de negocio traducidos de la valuacion del objeto de vista.
	 **/
    public List getBos() {
        return bos;
    }
    
    /**
	 * setter
	 * @param bos lista de objetos de negocio traducidos de la valuacion del objeto de vista.
	 **/
	 public void setBos(List bos) {
        this.bos = bos;
    }

	/**
	 * getter
	 * @return parametros tomados de la sesion web.
	 **/
    public Object getViewDTO() {
        return viewDTO;
    }
    
	/**
	 * setter
	 * @param viewDTO parametros tomados de la sesion web.
	 **/
    public void setViewDTO(Object viewDTO) {
        this.viewDTO = viewDTO;
    }

    /*public Desktop getDesktop() {
        return desktop;
    }

    public void setDesktop(Desktop desktop) {
        this.desktop = desktop;
    }*/
    
}
