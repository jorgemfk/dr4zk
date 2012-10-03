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
import mx.dr.forms.constants.DefaultValues;
import mx.dr.forms.constants.FellowType;
import mx.dr.forms.constants.FormActions;
import mx.dr.forms.view.component.DRLabel;
import mx.dr.forms.view.component.DRListBox;

/**
 *
 * <br/>
 * Anotacion que define el conjunto de caracteristicas que puede tener una accion que sera manejada por la clase del modelo de la vista.
 * @author Jorge Luis Martinez Ramirez
 * @version 1.0
 * @since 13/08/2012
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DRFellowLink{
    public FormActions action();
    /**
	 * ruta aboluta de la pagina y componente del tipo <code>include</code> especifico donde se cargara la nueva seccion de pantalla.
     * example //main/myWindow/myInclude
     * @return ruta aboluta. 
     */
    String componentPath() default DefaultValues.NONE;
	/**
	* tipo de resultado en que se mostrara el enlace.
	* @return tipo de resultado en que se mostrara el enlace.
	**/
    FellowType fellow() default FellowType.NEW;
	/**
	* accion que se realizara antes de enviar al enlace resultado, se recomienda que esta sea un metodo de una fachada: <code>pakage.FacadeClass@method</code>
	* @return accion 
	**/
    String submitAction() default DefaultValues.NONE;
	/**
	* al realizarse la accion sera la URL donde se dirigira el destino.
	* @return URL destino.
	**/
    String url() default DefaultValues.NONE;
	/**
	* colocar los IDs de los objetos a aextraer contenidos en el mapa de la http sesion de usuario.
	* @return parametros extraidos de la sesion http.
	**/
    String[] sessionParams() default {};
	/**
	* mensaje a mostrar cuando se realice la accion de forma exitosa.
	* @return mensaje.
	**/
    DRMessage sucessMessage() default @DRMessage;
    //edit list
	/**
	* para el caso exclusivo de una accion en la lista de resultados, se trata de la etiqueta que debera tener el enlace.
	* @return etiqueta del enlace.
	**/
    DRLabel listLabel() default @DRLabel(key=DefaultValues.NONE);
    /**
	* es el nombre del atributo del objeto de vista que se pasara como parametro de entrada para la accion.
	* @return nombre del atributo que se toma como parametro de entrada para la accion.
	**/
	String param() default DefaultValues.NONE;
	/**
	 * accion que se realizara antes de enviar al enlace resultado, se recomienda que esta sea un metodo de una fachada: <code>pakage.FacadeClass@method</code>
	 * @return accion 
	 **/	
    String paramAction() default DefaultValues.NONE;
    
    //search
	/**
	* es la definicion del listbox donde se pintaran los resultados de la busqueda, este es exclusivo de la accion de <code>search</code>
	* @return definicion de la lista de resultados.
	**/
    DRListBox resultsComponent() default @DRListBox;
	/**
	 * determina si habra un prellenado de resultados en el componente <code>resultComponent</code>, este es exclusivo de la accion de <code>search</code>
	 * @return definicion de la lista de resultados.
	 **/
    boolean loadOnInit() default false;
}
