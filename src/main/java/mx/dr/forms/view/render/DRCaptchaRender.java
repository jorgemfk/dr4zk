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

import mx.dr.forms.view.component.DRCaptcha;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Captcha;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

/**
 *
 * @author jorge
 */
public class DRCaptchaRender implements IDRRendereable<DRCaptcha,Object> {
    
    
    public Component render(DRCaptcha drCaptcha,final String name,final Object value,final Object dtoValue) throws Exception {
        Grid agrid = new Grid();
        agrid.appendChild(new Rows());
        Row orow = new Row();

        Captcha captcha = new Captcha();
        captcha.setId(name + DRCaptcha.FIELD_SUFIX);
        Textbox text = new Textbox();
        text.setId(name);

        Button abutton = new Button();
        abutton.setLabel(Labels.getLabel("dr.forms.label.regenerate"));
        abutton.setId(name+ DRCaptcha.BUTTON_SUFIX);
        //abutton.addForward(Events.ON_CLICK, comp, "drReCaptcha");

        abutton.addEventListener(Events.ON_CLICK, new EventListener() {

            public void onEvent(Event event) throws Exception {
                Component comp = event.getTarget();
                String id = comp.getId();
                System.out.println(id);
                comp = comp.getFellow(id.replaceFirst(DRCaptcha.BUTTON_SUFIX, "") + DRCaptcha.FIELD_SUFIX);
                ((Captcha) comp).randomValue();
            }
        });


        orow.appendChild(text);
        agrid.getRows().appendChild(orow);
        orow = new Row();
        orow.appendChild(captcha);
        agrid.getRows().appendChild(orow);
        orow = new Row();
        orow.appendChild(abutton);
        agrid.getRows().appendChild(orow);
        
        return agrid;
    }

    public Object value(DRCaptcha drInput, Component comp, Class<Object> expectedType) throws Exception {
        return ((Textbox)comp).getValue();
    }

    
}
