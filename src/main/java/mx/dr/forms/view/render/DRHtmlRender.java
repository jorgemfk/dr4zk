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

import mx.dr.forms.view.component.DRHtml;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Html;

/**
 *
 * @author jorge
 */
public class DRHtmlRender implements IDRRendereable<DRHtml,String> {
    
    
    public Component render(final DRHtml drHtml,final String name,final String value,final Object dtoValue)throws Exception {
        Html html = new Html();
        if (value != null) {
            html.setContent(value);
        }
        html.setId(name);
        return html;
    }

    public Object value(final DRHtml drHtml, final Component comp, final Class<String> expectedType) {
        return null;
    }

    
}
