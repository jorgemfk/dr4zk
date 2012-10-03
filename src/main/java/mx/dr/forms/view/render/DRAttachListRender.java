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

import java.util.ArrayList;
import java.util.List;

import mx.dr.forms.dto.DRMedia;
import mx.dr.forms.view.component.DRAttachList;
import mx.dr.forms.zul.DRGenericListModel;
import mx.dr.forms.zul.DRLimitedListModel;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Listbox;

/**
 *
 * @author jorge
 */
public class DRAttachListRender implements IDRRendereable<DRAttachList,Object> {
    
    
    public Component render(DRAttachList drAttachList,final String name,final Object value,final Object dtoValue) throws Exception {
       
        Listbox alist = new Listbox();
        alist.setWidth("400px");
        alist.setItemRenderer(drAttachList.itemRenderer().getName());
        alist.setId(name);
        List<DRMedia> medias = null;
        if (dtoValue != null) {
            medias = (List<DRMedia>) value;
            if (medias.size() < drAttachList.maxRow()) {
                medias.add(new DRMedia());
            }
        } else {
            medias = new ArrayList<DRMedia>();
            medias.add(new DRMedia());
        }
        alist.setModel(new DRLimitedListModel(medias, drAttachList.maxRow()));
        
        return alist;
    }

    public Object value(DRAttachList drInput, Component comp, Class<Object> expectedType) throws Exception {
        return  ((DRGenericListModel) ((Listbox) comp).getModel()).getResults();
    }

    
}
