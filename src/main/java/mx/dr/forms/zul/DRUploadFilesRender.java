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

import java.io.Serializable;
import java.util.List;

import mx.dr.forms.dto.DRAttachMedia;
import mx.dr.forms.dto.DRMedia;

import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Toolbarbutton;

/**
 * </br>
 * clase que pinta el comportamiento de un componente para la carga de archivos.
 * @version 1.0
 * @author Jorge Luis Martinez Ramirez
 * @since 13/08/2011
 * @since v0.5
 */ 
public class DRUploadFilesRender implements ListitemRenderer, Serializable {

    /**
	 * 
	 * @see org.zkoss.zul.ListitemRenderer#render
	 **/
    public void render(Listitem item, Object data) throws Exception {

        Listcell cell = null;

        cell = new Listcell();
        cell.setParent(item);
        DRMedia drMedia = (DRMedia) data;
        if (drMedia.getBo() == null && drMedia.getMedia() == null) {
            cell.appendChild(this.uploadActionButton(data, cell));
        } else {
            Grid grid= new Grid();
            cell.appendChild(grid);
            grid.appendChild(new Rows());
            Row row= new Row();
            grid.getRows().appendChild(row);
            System.out.println("row added");
            if (drMedia.getBo()!=null && drMedia.getBo().getContentType().startsWith("image")) {
                Image img = new Image();
                img.setSrc(drMedia.getBo().getUri());
                img.setWidth("100px");
                row.appendChild(img);
            } else if (drMedia.getMedia() != null && drMedia.getMedia() instanceof org.zkoss.image.Image) {
                Image img = new Image();
                img.setContent((org.zkoss.image.Image) drMedia.getMedia());
                img.setWidth("100px");
                row.appendChild(img);
            } else {
                downloadActionButton(drMedia.getMedia(), drMedia.getBo(), row);

            }
            deleteActionButton(drMedia, row);
        }

    }

    private Toolbarbutton uploadActionButton(final Object o,
            final Component parent) throws Exception {
        Toolbarbutton button = new Toolbarbutton();
        button.setSclass(Labels.getLabel("dr.forms.css.class.upload.upload"));
        button.setUpload("true,maxsize=300");
        button.addEventListener(Events.ON_UPLOAD, new EventListener() {

            public boolean isAsap() {
                return false;
            }

            public void onEvent(Event event) throws Exception {
                ((Listitem) parent.getParent()).setSelected(true);

                Media media = null;

                media = ((UploadEvent)event).getMedia();//Fileupload.get();

                if (media != null) {
                    System.out.println(media.getName() + " " + media.getFormat() + " " + media.getContentType());
                    DRMedia m = (DRMedia) o;
                    m.setMedia(media);
                    m.setName(media.getName());

                    System.out.println(parent.getParent().getParent());



                    Listbox listBox = (Listbox) parent.getParent().getParent();
                    List<DRMedia> medias = ((DRGenericListModel) listBox.getModel()).getResults();
                    int size = ((DRLimitedListModel) listBox.getModel()).getMaxSize();

                    if (medias.size() < size) {
                        medias.add(new DRMedia());
                        listBox.setModel(new DRLimitedListModel(medias, size));
                    } else {
                        Components.removeAllChildren(parent);
                        if (media instanceof org.zkoss.image.Image) {
                            Image img = new Image();
                            img.setContent((org.zkoss.image.Image) media);
                            img.setWidth("100px");
                            parent.appendChild(img);
                        } else {
                            downloadActionButton(media, null, parent);

                        }
                        deleteActionButton((DRMedia) o, parent);
                    }

                } else if (media == null) {
                    Messagebox.show("No se cargo la imagen " , "Error", Messagebox.OK, Messagebox.ERROR);
                }

            }
        });
        button.setLabel(Labels.getLabel("dr.forms.label.upload"));
        button.setParent(parent);
        return button;
    }

    private Toolbarbutton downloadActionButton(final Media o, final DRAttachMedia attach,
            final Component parent) throws Exception {
        Toolbarbutton button = new Toolbarbutton();
        button.setSclass(Labels.getLabel("dr.forms.css.class.upload.download"));
        button.addEventListener(Events.ON_CLICK, new EventListener() {

            public boolean isAsap() {
                return false;
            }

            public void onEvent(Event event) throws Exception {
                if (o != null) {
                    Media media = o;
                    Filedownload.save(media);
                } else {
                    Filedownload.save(attach.getUri(), attach.getContentType());
                }
            }
        });
        button.setLabel(Labels.getLabel("dr.forms.label.download"));
        button.setParent(parent);
        return button;
    }

    private Toolbarbutton deleteActionButton(final DRMedia o,
            final Component parent) throws Exception {
        Toolbarbutton button = new Toolbarbutton();
        button.setSclass(Labels.getLabel("dr.forms.css.class.upload.delete"));
        button.addEventListener(Events.ON_CLICK, new EventListener() {

            public boolean isAsap() {
                return false;
            }

            public void onEvent(Event event) throws Exception {
                Listbox listBox = (Listbox) parent.getParent().getParent().getParent().getParent().getParent();
                List<DRMedia> medias = ((DRGenericListModel) listBox.getModel()).getResults();
                int size = ((DRLimitedListModel) listBox.getModel()).getMaxSize();
                if (medias.size() == size) {
                    medias.add(new DRMedia());
                }
                medias.remove(o);
                listBox.setModel(new DRLimitedListModel(medias, size));

            }
        });
        button.setLabel(Labels.getLabel("dr.forms.label.delete"));
        button.setParent(parent);
        return button;
    }
    
    public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
		render(arg0, arg1);
	}
}
