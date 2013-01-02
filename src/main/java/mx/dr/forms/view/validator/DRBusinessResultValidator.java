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
import mx.dr.forms.view.validable.DRValidateBusinessResult;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;

/**
 *
 * @author jorge
 */
public class DRBusinessResultValidator implements IDRValidator<DRValidateBusinessResult, Object> {

    public String validate(DRValidateBusinessResult drInput, String name, Object value, Object dtoValue, String labelKey, Component parent, FormActions action) throws Exception {


        if (action.equals(FormActions.SEARCH) && !drInput.applySearch()) {
            return null;
        }

        if (dtoValue == null) {
            return null;
        }

        String[] invokerArray = drInput.action().split("@");
        Class objectInvoker = Class.forName(invokerArray[0]);
        Object facade = objectInvoker.newInstance();
        boolean res = (Boolean) objectInvoker.getMethod(invokerArray[1], dtoValue.getClass()).invoke(facade, dtoValue);
        System.out.println(res);
        Object[] param = {value};
        String msg = Labels.getLabel("dr.forms.label.empty.result", param);

        if (!((boolean) res)) {
            return msg;
        }

        return null;


    }
}
