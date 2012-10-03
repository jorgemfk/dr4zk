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
package mx.dr.forms.view.render;

import mx.dr.forms.constants.DefaultValues;
import mx.dr.forms.view.component.DRIntBox;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Intbox;

/**
 *
 * @author jorge
 */
public class DRIntBoxRender implements IDRRendereable<DRIntBox,Object> {
    
    
    public Component render(final DRIntBox drInputInt,final String name,final Object value,final Object dtoValue) throws Exception {
        Intbox intbox = new Intbox();
        intbox.setReadonly(drInputInt.readOnly());
        if (drInputInt.maxlenght() > DefaultValues.NO_LIMIT) {
            intbox.setMaxlength(drInputInt.maxlenght());
        }

        if (value != null) {
            intbox.setValue((Integer) value.getClass().getMethod("intValue").invoke(value));
        }
        intbox.setId(name);
        return intbox;
    }

    public Object value(final DRIntBox drInputInt, final Component comp, final Class<Object> expectedType) {
        return ((Intbox) comp).getValue();
    }

    
}
