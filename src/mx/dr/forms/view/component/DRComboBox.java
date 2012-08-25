/* 
 * Copyright (C) 2011 Jorge Luis Martinez Ramirez
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 *   Author: Jorge Luis Martinez Ramirez
 *   Email: jorgemfk1@gmail.com
 */
package mx.dr.forms.view.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import mx.dr.forms.constants.DefaultValues;
import mx.dr.forms.zul.DRComboSimpleRender;
/**
 *
 * @author Jorge Luis Martinez
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DRComboBox {
    public static final String ROUNDED_MOLD = "rounded";

    boolean autodrop() default true;
    String mold() default DRComboBox.ROUNDED_MOLD;
    Class itemRenderer() default DRComboSimpleRender.class;
    boolean buttonVisible() default true;
    String model();
    String action();
    String[] modelParams() default {};
    Class dtoResult() default Object.class;
    boolean header() default false;
}
