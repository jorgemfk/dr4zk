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
import java.util.Map;

import mx.dr.forms.constants.FellowType;
import mx.dr.forms.constants.FormActions;
import mx.dr.forms.dto.DRFieldDto;
import mx.dr.forms.view.DRActions;
import mx.dr.forms.view.DRFellowLink;
import mx.dr.forms.view.DRField;
import mx.dr.forms.view.utils.DRGeneralViewUtils;
import mx.dr.forms.view.utils.DtoConverter;
import mx.dr.util.ReflectionUtils;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

/**
 *
 * </br>
 * clase que pinta los resultados de una busqueda en un <code>listbox</code>
 * @version 1.0
 * @author Jorge Luis Martinez Ramirez
 * @since v0.5
 * @since 13/08/2011
 */
public class DRResultsListRender implements ListitemRenderer, Serializable {
    /**
	* Solo se pintaran aquellos atributos que hallan sido anotados como <code>drField</code> tipo <code>LIST</code>
	* @see org.zkoss.zul.ListitemRenderer#render
	**/
    public void render(Listitem item, Object data) throws Exception {


        Listcell cell = null;


        DRField drListField;
        Field field;
        for (DRFieldDto drfield : DtoConverter.getListFields(data.getClass())) {
            field = drfield.getField();
            field.setAccessible(true);
            drListField = DRGeneralViewUtils.readAnnotation(field, FormActions.LIST);
            if (drListField != null) {
                cell = new Listcell();
                //cell.setWidth(drListField.width());
                cell.setParent(item);
                cell.setLabel(field.get(data).toString());
            }
        }

        DRActions drListColumn = data.getClass().getAnnotation(DRActions.class);
        if (drListColumn != null) {
            for (DRFellowLink drFellow : drListColumn.actions()) {
                if (drFellow.action().equals(FormActions.LIST)) {
                    cell = new Listcell();
                    cell.setParent(item);
                    cell.appendChild(this.createActionButton(data, cell, drFellow));
                }

            }
        }


    }
    /**
	* @deprecared
	**/
    private Toolbarbutton createRemoveButton(final Object o,
            final Component parent, final DRFellowLink drFellow) throws Exception {
        final Toolbarbutton button = new Toolbarbutton();
        button.addEventListener("onClick", new EventListener() {

            public boolean isAsap() {
                return false;
            }

            public void onEvent(Event event) throws Exception {
                ((Listitem) parent.getParent()).setSelected(true);

                if (Messagebox.show("Esta seguro de querer eliminar?", "Eliminar", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == 1) {
                    //dao.delete(vo);
                    //((Listbox) parent.getParent().getParent()).getItems().remove(o);
                    //((DRGenericListModel)((Listbox) parent.getParent().getParent()).getModel()).remove(o);
                    Messagebox.show("Se elimino correctamente", "Eliminar",
                            Messagebox.OK, Messagebox.INFORMATION);

                }

            }
        });
        button.setLabel(Labels.getLabel(drFellow.listLabel().key()));
        button.setParent(parent);
        return button;
    }
    /**
	* genera una accion de acuerdo al comportamiento definido en el <code>DRFellowLink</code>
	* @param o objeto actual del modelo.
	* @param parent componente donde se colocara el boton.
	* @param drFellow directivas de accion a realizar.
	* @return un boton con la funcion de enlace deseada.
	**/
    private Toolbarbutton createActionButton(final Object o,
            final Component parent, final DRFellowLink drFellow) throws Exception {
        Toolbarbutton button = new Toolbarbutton();
        button.setSclass(Labels.getLabel("dr.forms.css.class.edit"));
        button.addEventListener(Events.ON_CLICK, new EventListener() {

            public boolean isAsap() {
                return false;
            }

            public void onEvent(Event event) throws Exception {
                ((Listitem) parent.getParent()).setSelected(true);
                StringBuffer sf = new StringBuffer(drFellow.url()).append("&").append(drFellow.param()).append("=").append(ReflectionUtils.genericGet(o, drFellow.param()));
                System.out.println("URL: " + sf);
                if (drFellow.fellow().equals(FellowType.NEW)) {
                    Executions.sendRedirect(sf.toString());
                } else if (drFellow.fellow().equals(FellowType.SELF)) {
                    ((Include) Path.getComponent(drFellow.componentPath())).setSrc(sf.toString());
                } else if (drFellow.fellow().equals(FellowType.POPUP)) {
                    Map params = DRGeneralViewUtils.uri2Params(sf.toString());
                    Window win = (Window) Executions.createComponents((String) params.get(DRGeneralViewUtils.URI_KEY), null, params);
                    win.setMaximizable(true);

                    win.doModal();

                }
            }
        });
        button.setLabel(Labels.getLabel(drFellow.listLabel().key()));
        button.setParent(parent);
        return button;
    }
    
    public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
		render(arg0, arg1);
	}
}
