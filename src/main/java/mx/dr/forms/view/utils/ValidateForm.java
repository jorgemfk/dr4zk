/*
*
*
* Copyright (C) 2011-2012 Jorge Luis Martinez Ramirez
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
package mx.dr.forms.view.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import mx.dr.forms.constants.FormActions;
import mx.dr.forms.dto.DRValidatorDefinition;
import mx.dr.forms.view.DRField;
import mx.dr.forms.view.validator.IDRValidator;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
/**
* <br/>
* Encargada de aplicar las validaciones correspondientes.
* @author Jorge Luis Martinez Ramirez
* @version 1.0
* @since 13/08/2011
* @since v0.5
*/
public class ValidateForm implements Serializable {

    /**
     * previene la creacion de instancias.
     */
    private ValidateForm() {
    }
    /**
    * evalua las validaciones aplicables a un atributo.
	* @param field atributo cuyo valor sera validado.
	* @param labelkey identificador de la etiqueta que describe el atributo.
	* @param vo objeto valuado del modelo de vista.
	* @param comp componente creado representante del atributo a validar.
	* @param listErrors lista de excepciones generadas por fallos en las validaciones.
	* @param action accion solicitada por el usuario.
	*/
    private static void evalComponent(Field field, String labelKey, Object vo, Component comp, List<WrongValueException> listErrors, FormActions action) throws Exception {
        Class<? extends IDRValidator> validator;
        String msg;
        for (DRValidatorDefinition definition : DRValidatorDefinition.define(field)) {
            validator = definition.getValidable();
            msg = validator.newInstance().validate(definition.getDrTag(), field.getName(), field.get(vo), vo, labelKey, comp, action);
            if (msg != null) {
                listErrors.add(new WrongValueException(comp.getFellow(field.getName()), msg));
            }
        }

    }
    /**
	* valida los atributos valuados de un objeto de modelo de vista.
	* @param vo objeto valuado del modelo de vista.
	* @param comp componente creado representante del atributo a validar.
	* @param listErrors lista de excepciones generadas por fallos en las validaciones.
	* @param action accion solicitada por el usuario.
	*/
    public static void validate(Object vo, Component comp, List<WrongValueException> listErrors, String action) throws Exception {

        DRField vc;
        DRField ec;

        FormActions eaction = FormActions.getValue(action);
        for (Field field : vo.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            vc = DRGeneralViewUtils.readAnnotation(field, eaction);
            if (vc != null) {

                if (!vc.isField()) {
                    validate(field.get(vo), comp, listErrors, action);
                } else {
                    evalComponent(field, vc.label().key(), vo, comp, listErrors, eaction);
                }
            }
        }
    }
    /**
	* @deprecated
	*/
    public static Grid String2Label(String message) {
        Grid g = new Grid();
        g.getChildren().add(new Rows());
        Row r;
        List l = g.getRows().getChildren();
        for (String m : message.split("@@")) {
            r = new Row();
            r.getChildren().add(new Label(m));
            l.add(r);
        }
        return g;
    }
}
