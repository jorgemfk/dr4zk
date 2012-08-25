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
import java.util.HashMap;
import java.util.Map;
import mx.dr.forms.constants.FellowType;
import mx.dr.forms.view.utils.DtoConverter;
import mx.dr.forms.view.DRFellowLink;
import mx.dr.forms.view.DRListColumn;
import mx.dr.forms.view.DRListField;
import mx.dr.forms.view.utils.DRGeneralViewUtils;
import mx.dr.util.ReflectionUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
/**
 *
 * @author Jorge Luis MArtinez
 */
public class DRResultsListRender implements ListitemRenderer,Serializable  {
    public void render(Listitem item, Object data) throws Exception {
		
			
			Listcell cell = null;
			

                        DRListField drListField;
			for(Field field:DtoConverter.getListFields(data.getClass())){
                               field.setAccessible(true);
                               drListField = field.getAnnotation(DRListField.class);
                               if(drListField!=null){
			       cell = new Listcell();
                               //cell.setWidth(drListField.width());
			       cell.setParent(item);
			       cell.setLabel(field.get(data).toString());
                               }
			}

                        DRListColumn drListColumn = data.getClass().getAnnotation(DRListColumn.class);
                        if(drListColumn!=null){
                            for(DRFellowLink drFellow: drListColumn.columns()){
                                cell = new Listcell();
                                cell.setParent(item);
                                cell.appendChild(this.createActionButton(data, cell,drFellow));

                            }
                        }
                        
		
	}

	private Toolbarbutton createRemoveButton(final Object o,
			final Component parent,final DRFellowLink drFellow) throws Exception{
		final
		Toolbarbutton button = new Toolbarbutton();
		button.addEventListener("onClick", new EventListener() {
			public boolean isAsap() {
				return false;
			}

			public void onEvent(Event event) throws Exception {
				((Listitem) parent.getParent()).setSelected(true);
				
					if(Messagebox.show("Esta seguro de querer eliminar?","Eliminar",Messagebox.OK| Messagebox.CANCEL , Messagebox.QUESTION)==1){
						//dao.delete(vo);
						//((Listbox) parent.getParent().getParent()).getItems().remove(o);
						//((DRGenericListModel)((Listbox) parent.getParent().getParent()).getModel()).remove(o);
						Messagebox.show("Se elimino correctamente", "Eliminar",
						        Messagebox.OK, Messagebox.INFORMATION);

					}
				
			}
		});
		button.setLabel(Labels.getLabel(drFellow.label().key()));
		button.setParent(parent);
		return button;
	}

	private Toolbarbutton createActionButton(final Object o,
            final Component parent, final DRFellowLink drFellow) throws Exception {
        Toolbarbutton button = new Toolbarbutton();
        button.setClass("btnverde2");
        button.addEventListener(Events.ON_CLICK, new EventListener() {

            public boolean isAsap() {
                return false;
            }

            public void onEvent(Event event) throws Exception {
                ((Listitem) parent.getParent()).setSelected(true);
                StringBuffer sf = new StringBuffer(drFellow.url()).append("&").append(drFellow.param()).append("=").append(ReflectionUtils.genericGet(o, drFellow.param()));
                System.out.println("URL: "+sf);
                if (drFellow.fellow().equals(FellowType.NEW)) {
                    Executions.sendRedirect(sf.toString());
                } else if (drFellow.fellow().equals(FellowType.SELF)) {
                    ((Include) Path.getComponent(drFellow.componentPath())).setSrc(sf.toString());
                } else if (drFellow.fellow().equals(FellowType.POPUP)) {
                    Map params = DRGeneralViewUtils.uri2Params(sf.toString());
                    Window win = (Window) Executions.createComponents((String)params.get(DRGeneralViewUtils.URI_KEY), null, params);
                    win.setMaximizable(true);

                    win.doModal();

                }
            }
        });
        button.setLabel(Labels.getLabel(drFellow.label().key()));
        button.setParent(parent);
        return button;
    }
}
