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
package mx.dr.forms.view.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import mx.dr.forms.constants.DefaultValues;
import mx.dr.forms.view.DRRender;
import mx.dr.forms.view.render.DRImageRender;
/**
 *
 * <br/>
 * Anotacion que define el comportamiento de un componente imagen
 * @author Jorge Luis Martinez Ramirez
 * @version 1.0
 * @since 13/08/2012
 * @since v0.5
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@DRRender(itemRenderer=DRImageRender.class)
public @interface DRImage {
   /**
   * anchura de la imagen.
   **/
   String width();
   /**
   * antepone este texto a la ruta donde se localiza la imagen.
   **/
   String prepend() default "";
   /**
   * si verdadero, rota cada x segundos la imagen a mostrar.
   **/
   boolean rotate() default false;
   /**
   * ruta de una imagen por defecto en caso de que en la ruta no exista la imagen.
   **/
   String nullimage() default "";
   /**
   * url a la que redirige en caso de que se de clic sobre la imagen.
   **/
   String href() default "";
   /**
   * atributos y sus valores que se pegaran en la url de la imagen como parametros.
   **/
   String[] hrefParams() default {};
   /**
   * de ser una lista de imagenes se desplegara la lista de imagenes a lo ancho, se refiere al minimo a mostrar.
   **/
   int minimages() default DefaultValues.NO_LIMIT;
}
