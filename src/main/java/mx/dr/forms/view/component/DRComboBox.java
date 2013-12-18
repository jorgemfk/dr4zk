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
import mx.dr.forms.view.DRRender;
import mx.dr.forms.view.render.DRComboBoxRender;
import mx.dr.forms.zul.DRComboSimpleRender;
/**
 *
 *<br/>
 * Anotacion que define el comportamiento de un componente combobox.
 * @author Jorge Luis Martinez Ramirez
 * @version 1.0
 * @since 13/08/2012
 * @since v0.5
 */ 
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@DRRender(itemRenderer=DRComboBoxRender.class)
public @interface DRComboBox {
    /**
	* molde por defecto y unico por el momento.
	**/
    public static enum MOLD {ROUNDED,DEFAULT};
    /**
	* despliega automaticamente la lista de valores posibles.
	**/
    boolean autodrop() default true;
	/**
	* estilo bajo el cual se va a pintar el combobox, solo hay uno
	**/
    MOLD mold() default DRComboBox.MOLD.ROUNDED;
	/**
	* clase que indica como se va a pintar el componente.
	**/
    Class itemRenderer() default DRComboSimpleRender.class;
	/**
	* determina si el boton para mostrar la lista de valores es visible.
	**/
    boolean buttonVisible() default true;
	/**
	* llama a la accion para llenar los valores del combo, esta accion debe regresar una coleccion de datos, 
	* <code>package.Facade@method</code>
	**/
    String model();
	/**
	 * llama a la accion que se realiza cuando el valor seleccionado en el combo no existe,  
	 * <code>package.Facade@method</code>
	 **/	
    String action();
	/**
	* nombre de atributos cuyos valores seran pasados como parametros para la accion del modelo.
	**/
    String[] modelParams() default {};
	/**
	* clase resultado
	**/
    Class dtoResult() default Object.class;
	/**
	**/
    boolean header() default false;
}
