/* 
 * Copyright (C) 2011 Jorge Luis Martinez Ramirez
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 *   Author: Jorge Luis Martinez Ramirez
 *   Email: jorgemfk1@gmail.com
 */
package mx.dr.forms.zul;

import java.lang.reflect.Field;
import mx.dr.forms.view.DRListField;
import mx.dr.util.ReflectionUtils;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

/**
 *
 * @author JLMR
 */
public class DRComboSimpleRender implements ComboitemRenderer {

    public void render(Comboitem item, Object o) throws Exception {

        DRListField drListField;
        Object dum;
        StringBuffer sf=new StringBuffer();;
        for (Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            drListField = field.getAnnotation(DRListField.class);
            if (drListField != null) {

                dum = ReflectionUtils.genericGet(o, field.getName());// field.get(data);
                sf.append(dum == null ? " " : dum.toString());
            }
        }
        item.setLabel(sf.toString());
        item.setValue(o);
        
    }
}
