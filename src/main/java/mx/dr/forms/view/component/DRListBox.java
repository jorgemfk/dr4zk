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
package mx.dr.forms.view.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import mx.dr.forms.constants.DefaultValues;
import mx.dr.forms.view.DRRender;
import mx.dr.forms.view.render.DRListBoxRender;
import mx.dr.forms.zul.DRResultsListSimpleRender;
/**
 *
 * <br/>
 * Anotacion que define el comportamiento de un componente listbox
 * @author Jorge Luis Martinez Ramirez
 * @version 1.0
 * @since 13/08/2012
 * @since v0.5
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@DRRender(itemRenderer=DRListBoxRender.class)
public @interface DRListBox {
    /**
	* formas de pintar el listbox
	**/
    public static enum MOLD {PAGING,SELECT};
	/**
	* identificador del listbox.
	**/
    String id() default DefaultValues.NONE;
	/**
	* estilo del molde para ser pintado.
	**/
    MOLD mold() default DRListBox.MOLD.PAGING;
	/**
	* clase que define como pintar las filas del listbox
	**/
    Class itemRenderer() default DRResultsListSimpleRender.class;
	/**
	* accion que determina como se cargara el modelo para llenar las filas del listbox, este metodo debe regresar una coleccion de datos.
	* <code>package.Facade@method</code>
	**/
    String model() default DefaultValues.NONE;
	/**
	* estilo css del mensaje de respuesta.
	**/
    String messageSclass() default DefaultValues.NONE;
	/**
	* nombre de los atributos que cuyos valores seran pasados como parametros de la accion de la carga de modelo.
	**/
    String[] modelParams() default {};
	
    Class dtoResult() default Object.class;
	/**
	* determina si el listbox llevara o no encabezado en sus columnas.
	**/
    boolean header() default false;
	/**
	* clase de estilos css.
	**/
    String sclass() default "";
    /**
     * page size / tamano de pagina
     */
    int pageSize() default 0;
}
