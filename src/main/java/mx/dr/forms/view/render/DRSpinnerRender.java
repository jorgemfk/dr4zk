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
import mx.dr.forms.view.component.DRSpinner;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Spinner;

/**
 *
 * @author jorge
 */
public class DRSpinnerRender implements IDRRendereable<DRSpinner,Integer> {
    
    
    public Component render(final DRSpinner drSpinner,final String name,final Integer value,final Object dtoValue) throws Exception {
        Spinner spinner = new Spinner();
        if (drSpinner.maxlenght() > DefaultValues.NO_LIMIT) {
            spinner.setMaxlength(drSpinner.maxlenght());
        }

        if (value != null) {
            spinner.setValue(value);
        }
        spinner.setId(name);
        return spinner;
    }

    public Object value(final DRSpinner drSpinner, final Component comp,final Class<Integer> expectedType) {
        return ((Spinner) comp).getValue();
    }
    
    

    
}
