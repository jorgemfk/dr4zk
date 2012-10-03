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
package mx.dr.forms.view.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mx.dr.forms.dto.DRAttachMedia;
import mx.dr.forms.dto.DRMedia;
import mx.dr.util.ReflectionUtils;

import org.zkoss.util.media.Media;

/**
 *
 * <br/>
 * Utilerias para el soporte de carga de archivos.
 * @author Jorge Luis Martinez Ramirez
 * @version 1.0
 * @since 13/08/2011
 * @since v0.5
 */
public class DRMediaUtils {
    private DRMediaUtils(){}

    public static List<DRMedia> buildMedias(Collection attachments) throws Exception{
		List<DRMedia> l=new ArrayList<DRMedia>();
		for(Object a: attachments){
			l.addAll(buildMedia((DRAttachMedia)a));
		}

		return l;
	}

    public static List<DRMedia> buildMedia(DRAttachMedia bo) throws Exception{
                List<DRMedia> l=new ArrayList<DRMedia>();
		DRMedia dto=null;
		dto=new DRMedia((String)ReflectionUtils.genericGet(bo, "name"));
		dto.setBo(bo);
                l.add(dto);
		return l;
    }

    public static Object bulidBO(Media media, Class objClass) throws Exception{
    	Object attach = objClass.newInstance();
    	ReflectionUtils.genericSet(attach, "name", media.getName());
    	ReflectionUtils.genericSet(attach, "format", media.getFormat().length()>4?media.getFormat().substring(media.getFormat().length()-3):media.getFormat());
    	ReflectionUtils.genericSet(attach, "contentType",media.getContentType());
    	return attach;
    }

}
