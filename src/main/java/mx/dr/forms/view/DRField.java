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
package mx.dr.forms.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import mx.dr.forms.constants.DefaultValues;
import mx.dr.forms.constants.FormActions;
import mx.dr.forms.view.component.DRLabel;

/**
 *
 * <br/>
 * Anotacion que define las caracteristicas generales de un atributo que representara un componente en la vista.
 * @author Jorge Luis Martinez Ramirez
 * @version 1.0
 * @since 13/08/2012
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DRField {
    /**
	*
	* enumeracion que representa el tipo de operacion de busqueda que puede manejar un atributo.
	**/
    public enum Operator { LIKE, EQUALS };
	/**
	* acciones a las que debera responder 
	* @return conjunto de acciones.
	**/
    FormActions[] actions();
	/**
	* determina si el atributo tendra un valor final o si es un objeto que encapsula otros atributos.
	* @return verdadero si es dato final o falso si es un objeto que encapsula otros atributos.
	**/
    boolean isField() default true;
	/**
	* determinq si al valor asignado al atributo debe validarse antes de presionar l boton de accion.
	* @return verdadero si se desea aplicar las validaciones antes de presionar el boton de accion.
	**/
    boolean liveValidate() default false;
	/**
	* etiqueta que se escribira antes del control, esta debe decribir la representacion del componente dibujado.
	* @return etiqueta del nombre del atributo en la vista.
	**/
    DRLabel label();
	/**
	* determina el orden en el que el componente es dibujado en la vista.
	* @return orden en el que va a ser dibujado en la pantalla.
	**/
    int order() default 0;
	/**
	 * identificador del componente padre donde se creara el componente hijo resultante, aplicable solo para la accion <code>READ</code>
	* @return identificador del componente padre donde se creara el componente hijo resultado.
	**/
    String readParent() default DefaultValues.NONE;
	/**
	* determina el operador que sera tratado al momento de hacer la busqueda.
	* @return operador que aplicara.
	**/
    Operator searchOperador() default Operator.EQUALS;
	/**
	* determina el ancho de la columna en los resultados de busqueda, aplicable solo para la accion <code>LIST</code>
	* ejemplo <code>100px</code>
	* @return valor de la anchura de la columna en pixeles.
	**/
    String columnListWidth() default "100px"; 
}
