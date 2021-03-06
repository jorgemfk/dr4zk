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
package mx.dr.forms.view.render;

import java.lang.annotation.Annotation;

import org.zkoss.zk.ui.Component;

/**
 *
 * @author jorge
 */
public interface IDRRendereable<T extends Annotation,V> {
    
    public <K extends Component> K  render(final T drInput,final String name,final V value,final Object dtoValue) throws Exception;

    public Object value(final T drInput,final Component comp,final Class<V>  expectedType) throws  Exception;
}
