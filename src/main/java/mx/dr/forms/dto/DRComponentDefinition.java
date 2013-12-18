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
import mx.dr.forms.view.DRRender;
import mx.dr.forms.view.render.IDRRendereable;

/**
 * DTO class that reads and encapsulates the elements necessary for the drawing of a component in the view.
 * </br>
 * Clase dto que lee y encapsula los elementos necesarios para el dibujado de un componente en la vista.
 * @version 1.0
 * @author Jorge Luis Martinez Ramirez
 * @since 13/08/2012
 */
public class DRComponentDefinition {
    /**
     * Custom annotation that defines the behavior of a component.
	* Anotacion personalizada que define el comportamiento de un componente.
	*
	**/
    private Annotation drTag;
	/**
	 * A class that must be defined as a component screen painting.
	* Clase que define como debe ser dibujado en pantalla un componente.
	*
	**/
    private Class<? extends IDRRendereable<? extends Annotation,? extends Object>>  renderable;
    /**
     * Default constructor prevents the creation of objects outside this class.
	* Constructor por defecto, previene la creacion de objetos fuera de esta clase.
	* @param drTag Custom annotation component. anotacion personalizada del componente.
	* @param renderable painting component class. clase que pinta el componente
	**/
    private DRComponentDefinition(Annotation drTag, Class<? extends IDRRendereable<? extends Annotation,? extends Object>>  renderable) {
        this.drTag = drTag;
        this.renderable = renderable;
    }
    /**
	* getter
	* @return Custom annotation that defines the behavior of a component. Anotacion personalizada que define el comportamiento de un componente.
	*
	**/
    public Annotation getDrTag() {
        return drTag;
    }
    /**
	* getter
	* @return Clase que define como debe ser dibujado en pantalla un componente.
	*
	**/
    public Class<? extends IDRRendereable<? extends Annotation,? extends Object>>  getRenderable() {
        return renderable;
    }
    /**
	* metodo que ayuda a encontrar la definicion de componente sobre un atributo.
	* @param field atributo para el cual se buscara una definicion de componente.
	* @return la definicion del componente a dibujar.
	**/
    public static DRComponentDefinition define(Field field) {
        DRRender drRender;
        Annotation drComponent = null;
        Class<? extends IDRRendereable<? extends Annotation,? extends Object>> renderable = null;
        field.setAccessible(true);


        for (Annotation anot : field.getAnnotations()) {
            drRender = anot.annotationType().getAnnotation(DRRender.class);
            
            if (drRender != null) {
                drComponent = anot;
                renderable = drRender.itemRenderer();
                break;
            }
        }

        return new DRComponentDefinition(drComponent, renderable);

    }
}
