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

import java.math.BigDecimal;

import mx.dr.forms.constants.DefaultValues;
import mx.dr.forms.view.component.DRDecimalBox;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Decimalbox;

/**
 *
 * @author jorge
 */
public class DRDecimalBoxRender implements IDRRendereable<DRDecimalBox, Object> {

    public Component render(final DRDecimalBox drInputDecimal, final String name, final Object value, final Object dtoValue) throws Exception {
        Decimalbox decimalBox = new Decimalbox();
        decimalBox.setReadonly(drInputDecimal.readOnly());
        decimalBox.setFormat(drInputDecimal.format());
        if (drInputDecimal.maxlenght() > DefaultValues.NO_LIMIT) {
            decimalBox.setMaxlength(drInputDecimal.maxlenght());
        }

        if (dtoValue != null) {
            if (value != null && value instanceof BigDecimal) {
                decimalBox.setValue((BigDecimal) value);
            } else if (value != null && value instanceof Double) {
                decimalBox.setValue(BigDecimal.valueOf((Double) value));
            }
        }
        decimalBox.setId(name);
        return decimalBox;
    }

    public Object value(final DRDecimalBox drInputDecimal, final Component comp, final Class<Object> expectedType) {

        if (expectedType.isAssignableFrom(Double.class)) {
            return ((Decimalbox) comp).getValue() == null ? null : ((Decimalbox) comp).getValue().doubleValue();
        } else {
            return ((Decimalbox) comp).getValue();
        }
    }
}
