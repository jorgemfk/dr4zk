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
package mx.dr.forms.view.utils;

import mx.dr.forms.constants.FormActions;
import mx.dr.forms.dto.DRValidatorDefinition;
import mx.dr.forms.view.validator.IDRValidator;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

/**
 *
 * <br/>
 * Encargada de ejecutar la validacion en vivo.
 * @author Jorge Luis Martinez Ramirez
 * @version 1.0
 * @since 13/08/2011
 * @since v0.5
 */
public class DRLiveConstraint implements Constraint {
  
    /**
	* arreglo de validaciones definidas para el atributo.
	**/
    DRValidatorDefinition[] definitions;
	/**
	* objeto de vista valuado con el atributo a validar.
	*/
    private Object dto;
	/**
	* identificador de la descripcion del atributo a validar.
	*/
    private String labelKey;
	/**
	* accion solicitada por el usuario.
	*/
    private FormActions action;
    
	
	/**
	 * constructor por defecto.
	 * @param definitions arreglo de validaciones definidas para el atributo.
	 **/
    public DRLiveConstraint(DRValidatorDefinition[] definitions) {
        this.definitions = definitions;
    }
    /**
	* aplica la validacion.
	* @see org.zkoss.zul.Constraint#validate
	*/
    public void validate(Component cmpnt, Object o) throws WrongValueException {

        Class<? extends IDRValidator> validator;
        String msg;
        for (DRValidatorDefinition definition : definitions) {
            validator = definition.getValidable();
            try {
                msg = validator.newInstance().validate(definition.getDrTag(), cmpnt.getId(), o, dto, labelKey, cmpnt.getParent(), action);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new WrongValueException(cmpnt, ex.toString());

            }

            if (msg != null) {
                throw new WrongValueException(cmpnt, msg);
            }
        }


    }
	/**
   	 * getter
	 * @return accion solicitada por el usuario.
	 */
    public FormActions getAction() {
        return action;
    }
    /**
   	 * setter
	 * @param action accion solicitada por el usuario.
	 */
    public void setAction(FormActions action) {
        this.action = action;
    }
    
	/**
     * getter
	 * @return objeto de vista valuado con el atributo a validar.
	 */
    public Object getDto() {
        return dto;
    }
	/**
	 * setter
	 * @param dto objeto de vista valuado con el atributo a validar.
	 */
    public void setDto(Object dto) {
        this.dto = dto;
    }
    
	/**
	* getter
	 * @return identificador de la descripcion del atributo a validar.
	 */
	 public String getLabelKey() {
        return labelKey;
    }
	/**
   	*  setter
	 * @param labelKey identificador de la descripcion del atributo a validar.
	 */
    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }
}
