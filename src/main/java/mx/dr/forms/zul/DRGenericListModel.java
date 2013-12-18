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
package mx.dr.forms.zul;

import java.util.List;

import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.event.ListDataEvent;

/**
 *
 * </br>
 * Modelo generico para asociar un arreglo de elementos a un componente de vista tipo lista.
 * @version 1.0
 * @author Jorge Luis Martinez Ramirez
 * @since 13/08/2011
 * @since v0.5
 */
public class DRGenericListModel extends AbstractListModel {
    /**
	* Constructor por defecto.
	* @param results arreglo de elementos que se van a asociar al componente.
	**/
	public DRGenericListModel(List results) {
		this.results = results;
		System.out.println(results.size());
	}
    /**
	 * arreglo de elementos que se van a asociar al componente.
	 **/
	private List results;
    /**
	 * getter
	 * @return arreglo de elementos que se van a asociar al componente.
	 **/
	public List getResults() {
		return results;
	}
    /**
	 * setter
	 * @param results arreglo de elementos que se van a asociar al componente.
	 **/
	public void setResults(List results) {
		this.results = results;
	}

	/**
	 * @see org.zkoss.zul.ListModel#getElementAt(int)
	 */
	public Object getElementAt(final int index) {
		return results.toArray()[index];
	}

	/**
	 * @see org.zkoss.zul.ListModel#getSize()
	 */
	public int getSize() {
		return results.toArray().length;
	}

    /**
	* elimina un elemento del modelo este debe evaluar <code>equals==true</code> con respecto algun elemento del arreglo del modelo.
	* @param bo elemento a eliminar.
	**/
	public void remove(final Object bo) {
		results.remove(bo);
		this.fireEvent(ListDataEvent.CONTENTS_CHANGED, 0, this.getSize() - 1);
	}
    /**
	* agrega un nuevo elemento al inicio del arreglo del modelo 
	* @param bo nuevo elemento a agregar en el modelo.
	**/
    public void add(final Object bo) {
		results.add(0,bo);
		this.fireEvent(ListDataEvent.CONTENTS_CHANGED, 0, this.getSize() - 1);
	}
    /**
	* Quita el ultimo elemento del modelo.
	**/
    public void removeLast() {
		results.remove(this.getSize()-1);
		this.fireEvent(ListDataEvent.CONTENTS_CHANGED, 0, this.getSize() - 1);
	}
}
