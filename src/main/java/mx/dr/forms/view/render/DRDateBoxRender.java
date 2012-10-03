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

import java.util.Date;

import mx.dr.forms.view.component.DRDateBox;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Datebox;

/**
 *
 * @author jorge
 */
public class DRDateBoxRender implements IDRRendereable<DRDateBox,Date> {
    
    
    public Component render(final DRDateBox drInputDate,final String name,final Date value,final Object dtoValue) throws Exception {
        Datebox datebox = new Datebox();
        datebox.setFormat(drInputDate.format());
        if (value != null) {
            datebox.setValue(value);
        }
        datebox.setId(name);
        return datebox;
    }

    public Object value(final DRDateBox drInputDate, final Component comp, final Class<Date> expectedType) {
        return ((Datebox) comp).getValue();
    }

    
}
