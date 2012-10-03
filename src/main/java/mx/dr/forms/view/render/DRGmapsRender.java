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

import mx.dr.forms.view.component.DRGmaps;
import mx.dr.util.ReflectionUtils;

import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.zk.ui.Component;

/**
 *
 * @author jorge
 */
public class DRGmapsRender implements IDRRendereable<DRGmaps,Object> {
    
    
    public Component render(final DRGmaps drGmaps,final String name,final Object value,final Object dtoValue)throws Exception {
        Gmaps gmaps = new Gmaps();
        gmaps.setWidth(drGmaps.width());
        gmaps.setHeight(drGmaps.height());
        gmaps.setId(name);
        gmaps.setShowLargeCtrl(drGmaps.showLargeCtrl());
        gmaps.setShowSmallCtrl(drGmaps.showSmallCtrl());
        if (dtoValue != null) {
            Double lat = (Double) ReflectionUtils.genericGet(dtoValue, drGmaps.latitude());
            Double lng = (Double) ReflectionUtils.genericGet(dtoValue, drGmaps.longitude());
            lat = lat == null? 0 : lat;
            lng = lng == null? 0 :lng;
            gmaps.setLat(lat);
            gmaps.setLng(lng);
            gmaps.appendChild(new Gmarker((String) ReflectionUtils.genericGet(dtoValue, drGmaps.gmarkContent()), lat, lng));
        }
        return gmaps;
    }

    public Object value(final DRGmaps drGmaps, Component comp, Class<Object> expectedType) {
        return null;
    }

    
}
