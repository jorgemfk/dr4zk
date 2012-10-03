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
package mx.dr.forms.dto;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import mx.dr.forms.view.DRValidate;
import mx.dr.forms.view.validator.IDRValidator;

/**
 *
 * </br>
 * Definicion de las partes para validar un atributo.
 * @version 1.0
 * @author Jorge Luis Martinez Ramirez
 * @since 13/08/2012
 */
public class DRValidatorDefinition {
    /**
	* anotacion que define el comportamiento de la validacion.
	**/
    private Annotation drTag;
	/**
	* clase que valida un atributo.
	**/
    private Class<? extends IDRValidator<? extends Annotation, ? extends Object>> validable;
    
    /**
	* Constructor por defecto.
	* @param drTag anotacion
	* @param validable ejecutor de la validacion.
	**/
    private DRValidatorDefinition(Annotation drTag, Class<? extends IDRValidator<? extends Annotation, ? extends Object>> validable) {
        this.drTag = drTag;
        this.validable = validable;
    }
    /**
	 * getter
	 * @return anotacion que define el comportamiento de la validacion.
	 **/
    public Annotation getDrTag() {
        return drTag;
    }
    /**
     * getter
	 * @return clase que valida un atributo.
	 **/
    public Class<? extends IDRValidator<? extends Annotation, ? extends Object>> getValidable() {
        return validable;
    }

    /**
	* obtiene la lista de validaciones aplicables a un atributo.
	* @param field atributo de la vista.
	* @return lista de validaciones aplicables.
	**/
    public static DRValidatorDefinition[] define(Field field) throws Exception{   
        DRValidate drValidate;
        List<DRValidatorDefinition> result = new ArrayList<DRValidatorDefinition>(0);
        
        for (Annotation anot : field.getAnnotations()) {
            drValidate = anot.annotationType().getAnnotation(DRValidate.class);
            if (drValidate != null) {
                result.add(new DRValidatorDefinition(anot, drValidate.itemValidate()));
            }
        }
        
        return result.toArray(new DRValidatorDefinition[result.size()]);

    }
}
