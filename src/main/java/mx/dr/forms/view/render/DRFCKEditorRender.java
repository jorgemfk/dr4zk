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

import mx.dr.forms.view.component.DRFCKEditor;

import org.zkforge.ckez.CKeditor;
import org.zkoss.zk.ui.Component;

/**
 *
 * @author jorge
 */
public class DRFCKEditorRender implements IDRRendereable<DRFCKEditor,String> {
    
    
    public Component render(final DRFCKEditor drFCKEditor,final String name,final String value,final Object dtoValue)throws Exception {
        CKeditor fckeditor = new CKeditor();
        fckeditor.setWidth(drFCKEditor.width());
        fckeditor.setHeight(drFCKEditor.height());
        fckeditor.setId(name);
        if (value != null) {
            fckeditor.setValue(value);
        }
        return fckeditor;
    }

    public Object value(final DRFCKEditor drFCKEditor, final Component comp, final Class<String> expectedType) {
        return ((CKeditor) comp).getValue();
    }

    
}
