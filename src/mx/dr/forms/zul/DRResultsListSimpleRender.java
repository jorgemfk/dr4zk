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

import java.io.Serializable;
import java.lang.reflect.Field;
import mx.dr.forms.view.DRListField;
import mx.dr.util.ReflectionUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;
/**
 *
 * @author Jorge Luis MArtinez
 */
public class DRResultsListSimpleRender implements ListitemRenderer,Serializable  {
    public void render(Listitem item, Object data) throws Exception {
		
			Listcell cell = null;
			cell = new Listcell();
			// The id of the media
                        DRListField drListField;
                        Object dum;
			for(Field field:data.getClass().getDeclaredFields()){
                               field.setAccessible(true);
                               drListField = field.getAnnotation(DRListField.class);
                               if(drListField!=null){
			       cell = new Listcell();
			       cell.setParent(item);
                               dum = ReflectionUtils.genericGet(data, field.getName());// field.get(data);
			       cell.setLabel(dum == null?" ":dum.toString());
                               }
			}

		
	}

}
