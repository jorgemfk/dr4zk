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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.dr.forms.constants.FormActions;
import mx.dr.forms.dto.GenericDtoIN;
import mx.dr.forms.view.DRActions;
import mx.dr.forms.view.DRFellowLink;
import mx.dr.forms.view.DRField;
import mx.dr.forms.zul.DRGenericListModel;
import mx.dr.util.ReflectionUtils;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;

/**
 *
 * <br/>
 * Utilerias varias para el apoyo del manejo de la vista.
 * @author Jorge Luis Martinez Ramirez
 * @version 1.0
 * @since 13/08/2011
 * @since v0.5
 */
public class DRGeneralViewUtils {
    /**
	* identificador de la uri en el mapa se sesion.
	**/
    public static final String URI_KEY = "uri";
    /**
	* previene la creacion de instancias.
	**/
    private DRGeneralViewUtils() {
    }
    /**
	* transforma los parametros pegados en una Url bajo la peticion GET a un mapa de parametros y valores.
	* @param uri url que tenga presentes parametros GET
	* @return mapa de parametros presentes en la url.
	*/
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
    /**
	* asigna el modelo de datos a un listbox
	* @param drSearchAction definicion del comportamiento de la pantalla de acuerdo a la accion SEARCH.
	* @param alist listbox de resultados.
	* @param dtoIn objeto valuado con los criterios de busqueda.
	* @param origin componente que aloja al listbox.
	*
	*/
    public static void fillListModel(DRFellowLink drSearchAction, Listbox alist, GenericDtoIN dtoIn, Component origin) throws Exception {
        String[] invokerArray = drSearchAction.submitAction().split("@");
        Class modelInvoker = Class.forName(invokerArray[0]);
        Object facade = modelInvoker.newInstance();
        System.out.println("in: "+ dtoIn.getViewDTO());
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
     
	 /**
	 * encuentra la definicion de un atributo de acuerdo a una accion.
	 * @param field atributo del que se leera la definicion del campo.
	 * @param action accion que invoca el usuario.
	 * @return definicion del comportamiento del atributo, deacuerdo a la accion solicitada.
	 **/
     public static DRField readAnnotation(Field field, FormActions action){
        DRField drfield = field.getAnnotation(DRField.class);
        if(drfield !=null){
            for(FormActions a:drfield.actions()){
                if(a.equals(action)){
                    return drfield;
                }
            }
        }
        return null;
    }
    
	/**
	 * encuentra la definicion de comportamiento de un modelo de vista de acuerdo a una accion.
	 * @param dtoClass clase de la que se leera la definicion del comportamiento.
	 * @param action accion que invoca el usuario.
	 * @return definicion del comportamiento de la vista, deacuerdo a la accion solicitada.
	 **/	
    public static DRFellowLink readAnnotation(Class<Object> dtoClass, FormActions action) {
        DRFellowLink drFellowLink = dtoClass.getAnnotation(DRFellowLink.class);
        if(drFellowLink!=null && drFellowLink.action().equals(action)){
            return drFellowLink;
        }
        
        DRActions dractions = dtoClass.getAnnotation(DRActions.class);
        if(dractions  !=null){
            for(DRFellowLink drfellowLink:dractions.actions()){
                if(drfellowLink.action().equals(action)){
                    return drfellowLink;
                }
            }
        }
        return null;
    } 
    /**
	* del mapa de sesion crea un mapa exclusivamente con los parametros solicitados.
	* @param sessionParams arreglo de parametros solicitados.
	* @param sessionScope mapa de parametros de session de usuario.
	* @return mapa resultante con los parametros solicitados.
	*/
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
