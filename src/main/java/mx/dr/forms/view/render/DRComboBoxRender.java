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

import java.util.List;

import mx.dr.forms.view.component.DRComboBox;
import mx.dr.forms.zul.DRGenericListModel;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;

/**
 *
 * @author jorge
 */
public class DRComboBoxRender implements IDRRendereable<DRComboBox, String> {

    public Component render(final DRComboBox drComboBox, final String name, final String value, final Object dtoValue) throws Exception {
        Combobox combobox = new Combobox();

        String[] invokerArray = drComboBox.model().split("@");
        Class modelInvoker = Class.forName(invokerArray[0]);
        Object facade = modelInvoker.newInstance();
        List results = null;
        results = (List) modelInvoker.getMethod(invokerArray[1]).invoke(facade);
        combobox.setModel(new DRGenericListModel(results));
        combobox.setItemRenderer(drComboBox.itemRenderer().getName());
        if (dtoValue != null) {
            combobox.setValue(value);
        }
        combobox.setId(name);
        return combobox;
    }

    public Object value(final DRComboBox drComboBox, final Component comp, final  Class<String> expectedType) throws Exception{
        if (((Combobox) comp).getSelectedIndex() != -1) {
            return ((DRGenericListModel) ((Combobox) comp).getModel()).getElementAt(((Combobox) comp).getSelectedIndex());
        } else {
            String newValue = ((Combobox) comp).getValue();
            if (!newValue.trim().equals("")) {
                String[] invokerArray = drComboBox.action().split("@");
                Class actionInvoker = Class.forName(invokerArray[0]);
                Object facade = actionInvoker.newInstance();
                Object created = actionInvoker.getMethod(invokerArray[1], String.class).invoke(facade, newValue);
                return created;
            }
            return null;
        }
    }
}
