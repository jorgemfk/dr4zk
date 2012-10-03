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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mx.dr.forms.constants.FormActions;
import mx.dr.forms.view.validable.DRValidateReferenceDate;
import mx.dr.util.DRGeneralUtils;
import mx.dr.util.ReflectionUtils;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;

/**
 *
 * @author jorge
 */
public class DRReferenceDateValidator implements IDRValidator<DRValidateReferenceDate, Date> {

    public String validate(DRValidateReferenceDate drInput, String name, Date value, Object dtoValue, String labelKey, Component parent, FormActions action) throws Exception {
        if(value ==null){
            return null;
        }
        
        if(action.equals(FormActions.SEARCH) && !drInput.applySearch()){
            return null;
        }
        
        
        Object refObj = ReflectionUtils.genericGet(dtoValue, drInput.property());
        if (refObj == null) {
            return Labels.getLabel("dr.forms.label.ref.null", DRGeneralUtils.argsAsArray(drInput.property()));

        }
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
        if (refObj instanceof Date && ((Date) value).before((Date) refObj)) {
            return Labels.getLabel("dr.forms.label.date.min", DRGeneralUtils.argsAsArray(Labels.getLabel(labelKey), sf.format((Date) refObj)));


        }
        if (refObj instanceof Integer) {
            Date date = DRGeneralUtils.addDays((Integer) refObj, Calendar.getInstance().getTime());
            if (drInput.mode().equals(DRValidateReferenceDate.MODE.MIN) && ((Date) value).before(date)) {
                return Labels.getLabel("dr.forms.label.date.min", DRGeneralUtils.argsAsArray(Labels.getLabel(labelKey), sf.format(date)));

            } else if (drInput.mode().equals(DRValidateReferenceDate.MODE.MAX) && ((Date) value).after(date)) {
                return Labels.getLabel("dr.forms.label.date.min", DRGeneralUtils.argsAsArray(Labels.getLabel(labelKey), sf.format(date)));

            }
        }


        return null;
    }
}
