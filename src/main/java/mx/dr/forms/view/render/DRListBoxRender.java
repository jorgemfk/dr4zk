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

import mx.dr.forms.constants.DefaultValues;
import mx.dr.forms.constants.FormActions;
import mx.dr.forms.dto.DRFieldDto;
import mx.dr.forms.dto.GenericDtoParamsIN;
import mx.dr.forms.view.DRActions;
import mx.dr.forms.view.DRFellowLink;
import mx.dr.forms.view.DRField;
import mx.dr.forms.view.component.DRListBox;
import mx.dr.forms.view.utils.DRGeneralViewUtils;
import mx.dr.forms.view.utils.DtoConverter;
import mx.dr.forms.zul.DRGenericListModel;
import mx.dr.util.ReflectionUtils;

import org.zkoss.util.resource.Labels;
import org.zkoss.zhtml.Div;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;

/**
 *
 * @author jorge
 */
public class DRListBoxRender implements IDRRendereable<DRListBox, Object> {
    
    public Component render(final DRListBox drListBox, final String name, final Object value, final Object dtoValue) throws Exception {
        
        Listbox alist = new Listbox();
        alist.setMold(drListBox.mold().name().toLowerCase());
        alist.setItemRenderer(drListBox.itemRenderer().getName());
        alist.setSclass(drListBox.sclass());
        
        if (!drListBox.id().equals(DefaultValues.NONE)) {
            alist.setId(drListBox.id());
            alist.setVisible(false);
        } else {
            alist.setId(name);
        }
        if (drListBox.pageSize()>0){
        	alist.setPageSize(drListBox.pageSize());
        }
        if (drListBox.header()) {
            alist.appendChild(new Listhead());
            DRField lField;
            Listheader head;
            Class listClass = drListBox.dtoResult();
            for (DRFieldDto f : DtoConverter.getListFields(listClass)) {
                lField = DRGeneralViewUtils.readAnnotation(f.getField(), FormActions.LIST);
                head = new Listheader(Labels.getLabel(lField.label().key()));
                head.setWidth(lField.columnListWidth());
                alist.getListhead().appendChild(head);
            }
            DRActions drListColumn = (DRActions) listClass.getAnnotation(DRActions.class);
            if (drListColumn != null) {
                for (DRFellowLink drFellow : drListColumn.actions()) {
                    if (drFellow.action().equals(FormActions.LIST)) {
                        head = new Listheader(Labels.getLabel(drFellow.listLabel().key()));
                        
                        alist.getListhead().appendChild(head);
                    }
                    
                }
            }
            
        }
        List results = null;
        
        if (!drListBox.model().equals(DefaultValues.NONE)) {
            String[] invokerArray = drListBox.model().split("@");
            Class modelInvoker = Class.forName(invokerArray[0]);
            Object facade = modelInvoker.newInstance();
            
            if (drListBox.modelParams().length > 0) {
                GenericDtoParamsIN dtoParams = new GenericDtoParamsIN();
                if (dtoValue != null) {
                    for (String s : drListBox.modelParams()) {
                        
                        
                        dtoParams.getParams().put(s, ReflectionUtils.genericGet(dtoValue, s));
                    }
                    
                }
                results = (List) modelInvoker.getMethod(invokerArray[1], dtoParams.getClass()).invoke(facade, dtoParams);
            } else {
                results = (List) modelInvoker.getMethod(invokerArray[1]).invoke(facade);
            }
            
            alist.setModel(new DRGenericListModel(results));
            if (dtoValue != null) {
                if (value != null) {
                    for (Object o : results) {
                        if (value.equals(o)) {
                            alist.setSelectedIndex(results.indexOf(o));
                        }
                    }
                }
            }
        }
        Div div = null;
        if (drListBox.mold().equals(DRListBox.MOLD.PAGING)) {
            div = new Div();
            Label message = new Label(Labels.getLabel("dr.forms.results.empty"));
            message.setSclass(drListBox.messageSclass());
            message.setId(new StringBuffer(alist.getId()).append("_msg").toString());
            div.appendChild(message);
            message.setVisible(results == null ? false : results.isEmpty());
            div.appendChild(alist);
        }
        //alist.setWidth("660px");

        return div == null ? alist : div;
    }
    
    public Object value(final DRListBox drListBox, final Component comp, final Class<Object> expectedType) {
        Listbox list = (Listbox) comp;
        //TODO mejorar, se selecciona el 1er elemento
        if (list.getSelectedIndex() < 0) {
            list.setSelectedIndex(0);
        }
        return ((DRGenericListModel) list.getModel()).getElementAt(list.getSelectedIndex());
    }
}
