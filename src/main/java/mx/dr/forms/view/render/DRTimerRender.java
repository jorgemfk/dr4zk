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

import mx.dr.forms.view.component.DRTimer;
import mx.dr.util.DRGeneralUtils;

import org.zkoss.zhtml.Div;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Label;
import org.zkoss.zul.Timer;

/**
 *
 * @author jorge
 */
public class DRTimerRender implements IDRRendereable<DRTimer,Date> {
    
    
    public Div render(final DRTimer drTimer,final String name,final Date value,final Object dtoValue) throws Exception {
        Timer timer = new Timer(drTimer.delay());
        timer.setRepeats(drTimer.repeats());
        final Date end = value;
        final Label label = new Label();
        timer.addEventListener(Events.ON_TIMER, new EventListener() {

            public void onEvent(Event event) throws Exception {
                label.setValue(DRGeneralUtils.endTime(end));
            }
        });
        Div div= new Div();
        div.appendChild(timer);
        div.appendChild(label);
        return div;
    }

    public Object value(final DRTimer drTimer,final Component comp,final Class<Date> expectedType) {
        return null;
    }

    
}
