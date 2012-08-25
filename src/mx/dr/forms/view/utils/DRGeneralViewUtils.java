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
package mx.dr.forms.view.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.dr.forms.dto.GenericDtoIN;
import mx.dr.forms.view.DRSearchAction;
import mx.dr.forms.zul.DRGenericListModel;
import mx.dr.util.ReflectionUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;

/**
 *
 * @author JLMR
 */
public class DRGeneralViewUtils {

    public static final String URI_KEY = "uri";

    private DRGeneralViewUtils() {
    }

    public static Map<String, Object> uri2Params(String uri) {
        String[] uriduo = uri.split("\\?");
        Map params = new HashMap();
        params.put(URI_KEY, uriduo[0]);
        for (String s : uriduo[1].split("&")) {
            String[] p = s.split("=");
            params.put(p[0], p[1]);

        }
        System.out.println(params);
        System.out.println(params.values());

        return params;
    }

    public static void fillListModel(DRSearchAction drSearchAction, Listbox alist, GenericDtoIN dtoIn, Component origin) throws Exception {
        String[] invokerArray = drSearchAction.action().split("@");
        Class modelInvoker = Class.forName(invokerArray[0]);
        Object facade = modelInvoker.newInstance();
        List results = DtoConverter.buildResults((List) modelInvoker.getMethod(invokerArray[1], GenericDtoIN.class).invoke(facade, dtoIn), drSearchAction.resultsComponent().dtoResult());
        if (results.isEmpty()) {
            alist.setVisible(false);
            Label message = (Label)origin.getFellow(new StringBuffer(alist.getId()).append("_msg").toString());
            message.setVisible(true);
        } else {
            alist.setVisible(true);
            alist.setModel(new DRGenericListModel((results)));
            Label message = (Label)origin.getFellow(new StringBuffer(alist.getId()).append("_msg").toString());
            message.setVisible(false);
        }
    }

    public static Map<String, Object> buildMapSessionParams(String[] sessionParams, Map sessionScope) throws Exception {
        String[] fieldParams;
        Object obj = null;
        Map params = new HashMap();
        if (sessionParams.length > 0) {
            for (String s : sessionParams) {
                fieldParams = s.split("\\.");
                for (int i = 0; i < fieldParams.length; i++) {
                    if (i == 0) {
                        obj = sessionScope.get(fieldParams[i]);
                    } else {
                        obj = ReflectionUtils.genericGet(obj, fieldParams[i]);
                        if (obj == null) {
                            break;
                        }
                    }
                }
                params.put(s, obj);
            }
        }
        return params;
    }
}
