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
package mx.dr.forms.view.validator;

import java.lang.annotation.Annotation;

import mx.dr.forms.constants.FormActions;

import org.zkoss.zk.ui.Component;

/**
 *
 * </br>
 * clase que pinta cada elemento contenido en un combo, la etiqueta deseada debe ser anotada en el atributo como <code>drField</code> con accion <code>LIST</code>.
 * @version 1.0
 * @author Jorge Luis Martinez Ramirez
 * @since 13/08/2012
 * @since v0.9
 */
public interface IDRValidator<T extends Annotation,V> {
    
    /**
     * 
     * @param drInput
     * @param name
     * @param value
     * @param dtoValue
     * @param labelKey
     * @param parent
     * @param action
     * @return
     * @throws Exception 
     */
    public String validate(final T drInput,final String name,final V value,final Object dtoValue,final String labelKey, Component parent, FormActions action) throws Exception;
    
    
    
}
