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

import java.util.Collection;
import java.util.List;

import mx.dr.forms.dto.DRAttachMedia;
import mx.dr.forms.view.component.DRImage;
import mx.dr.util.ReflectionUtils;

import org.zkoss.util.resource.Labels;
import org.zkoss.zhtml.Div;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Timer;

/**
 *
 * @author jorge
 */
public class DRImageRender implements IDRRendereable<DRImage,Object> {
    
    
    public Component render(final DRImage drImage,final String name,final Object value,final Object dtoValue) throws Exception {
       Image image = new Image();
        image.setWidth(drImage.width());
        image.setId(name);
        Div div=new Div();
        if (dtoValue != null) {
            if (drImage.rotate()) {
                final List list = (List) value;
                if (list != null && !list.isEmpty()) {
                    Timer timer = new Timer(5000);
                    timer.setRepeats(true);
                    final Image img=image;
                    timer.addEventListener(Events.ON_TIMER, new EventListener() {

                        private int current = 1;

                        public void onEvent(Event event) throws Exception {
                            if (current == list.size()) {
                                current = 0;
                            }
                            DRAttachMedia att = (DRAttachMedia) list.get(current);
                            img.setSrc(new StringBuffer(drImage.prepend()).append(att.getUri()).toString());
                            current++;
                        }
                    });
                    image.setSrc(new StringBuffer(drImage.prepend()).append(((DRAttachMedia) list.get(0)).getUri()).toString());
                    div.appendChild(timer);
                }else{
                    Label label = new Label(Labels.getLabel("dr.forms.msg.noimage"));
                    div.appendChild(label);
                }
            } else {
                if (value instanceof Collection){
                    Grid grid = new Grid();
                    grid.appendChild(new Rows());
                    Row row= new Row();
                    grid.getRows().appendChild(row);
                    for(DRAttachMedia o :(Collection<DRAttachMedia>)value){
                        image.setSrc(new StringBuffer(drImage.prepend()).append(o.getUri()).toString());
                        row.appendChild(image);
                        image = new Image();
                        image.setWidth(drImage.width());
                    }
                    int min= drImage.minimages()-((Collection<DRAttachMedia>)value).size();
                    if(min>0){
                        for(int k=0; k<min;k++){
                         image.setSrc(new StringBuffer(drImage.prepend()).append(drImage.nullimage()).toString());
                        row.appendChild(image);
                        image = new Image();
                        image.setWidth(drImage.width());
                        }
                    }
                    div.appendChild(grid);
                    return null;
                }else{
                StringBuffer sf = new StringBuffer(drImage.prepend()).append((String) value);
                System.out.println(sf);
                image.setSrc(sf.toString());
                if(!drImage.href().equals("")){
                    image.setStyle("cursor: pointer;");
                    Object[] params =  new Object[drImage.hrefParams().length];
                    for(int i=0; i<drImage.hrefParams().length;i++){
                        params[i] =  ReflectionUtils.genericGet(dtoValue, drImage.hrefParams()[i]);
                    }
                    final String uri=Labels.getLabel(drImage.href(), params);

                    image.addEventListener("onClick", new EventListener() {

                            public void onEvent(Event event) throws Exception {

                                Executions.sendRedirect(uri);
                            }
                        });
                }


                }
            }
        }
        div.appendChild(image);
        return div;
    }

    public Object value(final DRImage drImage, final Component comp, final Class<Object> expectedType) {
        return null;
    }

    
}
