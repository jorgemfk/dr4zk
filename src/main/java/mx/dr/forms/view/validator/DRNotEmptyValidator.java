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
package mx.dr.forms.view.validator;

import java.util.Collection;

import mx.dr.forms.constants.DefaultValues;
import mx.dr.forms.constants.FormActions;
import mx.dr.forms.dto.DRMedia;
import mx.dr.forms.view.validable.DRValidateNotEmpty;
import mx.dr.util.ReflectionUtils;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;

/**
 *
 * @author jorge
 */
public class DRNotEmptyValidator implements IDRValidator<DRValidateNotEmpty, Object> {
    
    public String validate(DRValidateNotEmpty drInput, String name, Object value, Object dtoValue, String labelKey, Component parent, FormActions action) throws Exception{
        
        
        if(action.equals(FormActions.SEARCH) && !drInput.applySearch()){
            return null;
        }
        
        
        Object[] param={Labels.getLabel(labelKey)};
        String msg =Labels.getLabel("dr.forms.label.required",param);
        if(value == null){
            return msg;
        }
        else if(value instanceof String && ((String)value).trim().equals("")){
            return msg;
        }
        else if(value instanceof Collection && (((Collection)value).isEmpty() || ((DRMedia)((Collection)value).toArray()[0]).getNotUploaded())){
            return msg;
        }
        else if(!drInput.property().equals(DefaultValues.NONE) && (ReflectionUtils.genericGet(value, drInput.property())).toString().equals(drInput.emptyValue())){
            return msg;
        }
        return null;
    }
    
}
