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

import java.io.Serializable;
import java.lang.reflect.Field;

import mx.dr.forms.constants.FormActions;
import mx.dr.forms.view.DRField;
import mx.dr.forms.view.utils.DRGeneralViewUtils;
import mx.dr.util.ReflectionUtils;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
/**
 *
 * </br>
 * clase que pinta los resultados de una busqueda en un <code>listbox</code> sin acciones adicionales.
 * @version 1.0
 * @author Jorge Luis Martinez Ramirez
 * @since 13/08/2011
 * @since v0.5
 */
public class DRResultsListSimpleRender implements ListitemRenderer,Serializable  {
    /**
	 * Solo se pintaran aquellos atributos que hallan sido anotados como <code>drField</code> tipo <code>LIST</code>
	 * @see org.zkoss.zul.ListitemRenderer#render
	 **/
	public void render(Listitem item, Object data) throws Exception {
		
			Listcell cell = null;
			cell = new Listcell();
			// The id of the media
                        DRField drListField;
                        Object dum;
			for(Field field:data.getClass().getDeclaredFields()){
                               field.setAccessible(true);
                               drListField =  DRGeneralViewUtils.readAnnotation(field, FormActions.LIST);
                               if(drListField!=null){
			       cell = new Listcell();
			       cell.setParent(item);
                               dum = ReflectionUtils.genericGet(data, field.getName());// field.get(data);
			       cell.setLabel(dum == null?" ":dum.toString());
                               }
			}

		
	}
	
	 public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
			render(arg0, arg1);
		}

}
