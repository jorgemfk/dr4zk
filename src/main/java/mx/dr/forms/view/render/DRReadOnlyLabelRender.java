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
import mx.dr.forms.view.component.DRLabel;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;

/**
 *
 * @author jorge
 */
public class DRReadOnlyLabelRender implements IDRRendereable<DRLabel, Object> {
    
    
    public Component render(final DRLabel drLabel,final String name,final Object value,final Object dtoValue) throws Exception { 
        Label label = new Label(value != null ? value.toString() : " ");
        if (!drLabel.sclass().equals(DefaultValues.NONE)) {
            label.setZclass(drLabel.sclass());
        }
        return label;
    }

    public Object value(final DRLabel drLabel, final Component comp,final Class<Object> expectedType) {
        return null;
    }

    
}
