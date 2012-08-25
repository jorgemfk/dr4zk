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
package mx.dr.forms.zul;

import java.util.List;
import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.event.ListDataEvent;

/**
 *
 * @author Jorge Luis Martinez
 */
public class DRGenericListModel extends AbstractListModel {

	public DRGenericListModel(List results) {
		this.results = results;
		System.out.println(results.size());
	}

	private List results;

	public List getResults() {
		return results;
	}

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


	public void remove(final Object bo) {
		results.remove(bo);
		this.fireEvent(ListDataEvent.CONTENTS_CHANGED, 0, this.getSize() - 1);
	}

        public void add(final Object bo) {
		results.add(0,bo);
		this.fireEvent(ListDataEvent.CONTENTS_CHANGED, 0, this.getSize() - 1);
	}

        public void removeLast() {
		results.remove(this.getSize()-1);
		this.fireEvent(ListDataEvent.CONTENTS_CHANGED, 0, this.getSize() - 1);
	}
}
