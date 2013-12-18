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
package mx.dr.forms.zul;

import java.lang.reflect.Field;

import mx.dr.forms.constants.FormActions;
import mx.dr.forms.view.DRField;
import mx.dr.forms.view.utils.DRGeneralViewUtils;
import mx.dr.util.ReflectionUtils;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

/**
 *
 * </br>
 * clase que pinta cada elemento contenido en un combo, la etiqueta deseada debe ser anotada en el atributo como <code>drField</code> con accion <code>LIST</code>.
 * @version 1.0
 * @author Jorge Luis Martinez Ramirez
 * @since 13/08/2011
 * @since v0.5
 */
public class DRComboSimpleRender implements ComboitemRenderer {
    /**
	* Pinta un nuevo elemento a un combo.
	* @param o elemento del cual se extraera la etiqueta a pintar.
	* @param item componente tipo combo al que se le agregaran los nuevos elementos.
	**/
    public void render(Comboitem item, Object o) throws Exception {

        DRField drListField;
        Object dum;
        StringBuffer sf=new StringBuffer();
        for (Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            drListField =  DRGeneralViewUtils.readAnnotation(field, FormActions.LIST);
            if (drListField != null) {

                dum = ReflectionUtils.genericGet(o, field.getName());// field.get(data);
                sf.append(dum == null ? " " : dum.toString());
            }
        }
        item.setLabel(sf.toString());
        item.setValue(o);
        
    }

	public void render(Comboitem arg0, Object arg1, int arg2) throws Exception {
		render(arg0, arg1);
	}
}
