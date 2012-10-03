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

import mx.dr.forms.constants.FormActions;
import mx.dr.forms.view.validable.DRValidateLenght;
import mx.dr.util.DRGeneralUtils;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;

/**
 *
 * @author jorge
 */
public class DRLenghtValidator implements IDRValidator<DRValidateLenght, Object> {

    public String validate(DRValidateLenght drInput, String name, Object value, Object dtoValue, String labelKey, Component parent, FormActions action) throws Exception {


        if (value == null) {
            return null;
        }
        
        if(action.equals(FormActions.SEARCH) && !drInput.applySearch()){
            return null;
        }

        if (value  instanceof Integer) {
            if (drInput.mode().equals(DRValidateLenght.MODE.MIN)) {
                if (((Integer) value) < drInput.lenght()) {
                    return Labels.getLabel("dr.forms.label.spinner.min", DRGeneralUtils.argsAsArray(Labels.getLabel(labelKey), drInput.lenght()));

                }
            } else if (drInput.mode().equals(DRValidateLenght.MODE.MAX)) {
                if (((Integer) value) > drInput.lenght()) {
                    return Labels.getLabel("dr.forms.label.spinner.max", DRGeneralUtils.argsAsArray(Labels.getLabel(labelKey), drInput.lenght()));

                }
            }
        } else if (value  instanceof String) {
            if (drInput.mode().equals(DRValidateLenght.MODE.MIN)) {
                if (((String) value).trim().length() < drInput.lenght()) {
                    Object[] param = {Labels.getLabel(labelKey), drInput.lenght(), ((String) value).trim().length()};
                    return Labels.getLabel("dr.forms.label.min.lenght", param);

                }
            } else if (drInput.mode().equals(DRValidateLenght.MODE.MAX)) {
                if (((String) value).trim().length() > drInput.lenght()) {
                    Object[] param = {Labels.getLabel(labelKey), drInput.lenght(), ((String) value).trim().length()};
                    return Labels.getLabel("dr.forms.label.maxlenght", param);

                }
            }
        }


        return null;
    }
}
