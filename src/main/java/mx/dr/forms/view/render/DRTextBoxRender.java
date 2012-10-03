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

import mx.dr.forms.constants.DefaultValues;
import mx.dr.forms.view.component.DRTextBox;

import org.zkoss.zhtml.Div;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Html;
import org.zkoss.zul.Textbox;

/**
 *
 * @author jorge
 */
public class DRTextBoxRender implements IDRRendereable<DRTextBox,String> {
    
    
    public Component render(final DRTextBox drInputText, final String name,final String value,final Object dtoValue) throws Exception {

        Textbox atext = new Textbox();
        atext.setRows(drInputText.rows());
        atext.setType(drInputText.type().name());
        atext.setReadonly(drInputText.readOnly());
        atext.setCols(drInputText.cols());
        if(drInputText.uppercase()){atext.setStyle("text-transform:uppercase;");}

        if (drInputText.maxlenght() > DefaultValues.NO_LIMIT) {
            atext.setMaxlength(drInputText.maxlenght());
        }

        if (value != null) {
            atext.setValue(value);
        }
        atext.setId(name);
        Div div=null; 
 
        if(!drInputText.mask().equals(DefaultValues.NONE)){
            div =  new Div();
            div.appendChild(atext);
            div.appendChild(new Html(
                    new StringBuffer()
                    .append("<script type=\"text/javascript\">")
                    .append("$('#")
                    .append(atext.getUuid())
                    .append("').mask('")
                    .append(drInputText.mask())
                    .append("');")
                    .append("</script>")
                    .toString()

                    ));
         }
        return div==null?atext:div;

    }

    public Object value(final DRTextBox drInputText,final Component comp,final Class<String> expectedType) {
 
        if(comp instanceof Textbox){
            return ((Textbox) comp).getValue();
        }
            
        return null;   
    }

    

    
}
