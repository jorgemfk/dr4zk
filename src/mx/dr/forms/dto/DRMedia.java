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
package mx.dr.forms.dto;

import org.zkoss.util.media.Media;

/**
 *
 * @author jorge
 */
public class DRMedia {
    private String name;
    private Media media;
    private DRAttachMedia bo;

    public DRMedia() {
    }

    public DRMedia(String name) {
        this.name = name;
    }


    public DRAttachMedia getBo() {
        return bo;
    }

    public void setBo(DRAttachMedia bo) {
        this.bo = bo;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public boolean getUploaded() {
        return media != null || bo != null;
    }

    public boolean getNotUploaded() {
        return media == null && bo == null;
    }

    public boolean isAttachLoaded() {
        return bo != null;
    }

    public boolean isNotAttachLoaded() {
        return bo == null;
    }
}
