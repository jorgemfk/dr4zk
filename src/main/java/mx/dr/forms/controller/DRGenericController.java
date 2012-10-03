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
package mx.dr.forms.controller;

import mx.dr.forms.constants.FellowType;
import mx.dr.forms.constants.FormActions;
import mx.dr.forms.dto.GenericDtoIN;
import mx.dr.forms.view.utils.DtoConverter;
import mx.dr.forms.view.DRFellowLink;
import mx.dr.forms.view.DRMessage;
import mx.dr.forms.view.utils.DRGeneralViewUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * </br>
 * Clase que define el comportamiento general al realizar la accion de crear o actualizar.
 * @version 1.0
 * @author Jorge Luis Martinez Ramirez
 * @since 13/08/2012
 */
public class DRGenericController extends DRGenericControllerAbstract {
	/**
	 *
	 * Metodo que implementa el comportamiento generico de una accion de crear o actualizar.
	 * @see mx.dr.forms.DRGenericController#doSomething
	 **/
    protected void doSomething(Object o) throws Exception {
        GenericDtoIN dtoIN = DtoConverter.convert(o);

        String[] invokerArray = null;
        FellowType ft = null;
        String url = null;
        String componentPath = null;
        DRMessage msg = null;
        if (action.equals(FormActions.ADD.name())) {
            DRFellowLink drAddAction = DRGeneralViewUtils.readAnnotation(dtoClass, FormActions.ADD);
            invokerArray = drAddAction.submitAction().split("@");
            ft = drAddAction.fellow();
            url = drAddAction.url();
            componentPath = drAddAction.componentPath();

            dtoIN.setSessionParams(DRGeneralViewUtils.buildMapSessionParams(drAddAction.sessionParams(), sessionScope));
            msg = drAddAction.sucessMessage();
        } else if (action.equals(FormActions.EDIT.name())) {

            DRFellowLink drEditAction = DRGeneralViewUtils.readAnnotation(dtoClass, FormActions.EDIT);
            Object bo = findSuchObject(param, dtoClass, drEditAction);
            //parece que hay rutinas repetidas solo funciona para un objeto
            //en anuncio y empresa service se repite esta rutina al editar
            DtoConverter.buildBO(o, null, bo, true);
            //TODO hacerlo generic for
            dtoIN.getBos().clear();
            dtoIN.getBos().add(bo);

            invokerArray = drEditAction.submitAction().split("@");
            ft = drEditAction.fellow();
            url = drEditAction.url();
            componentPath = drEditAction.componentPath();
            dtoIN.setSessionParams(DRGeneralViewUtils.buildMapSessionParams(drEditAction.sessionParams(), sessionScope));
            msg = drEditAction.sucessMessage();
        }
        Class modelInvoker = Class.forName(invokerArray[0]);
        Object facade = modelInvoker.newInstance();
        modelInvoker.getMethod(invokerArray[1], GenericDtoIN.class).invoke(facade, dtoIN);

        if (msg != null && msg.label() != null && msg.mold().equals(DRMessage.Mold.POPUP)) {
            Messagebox.show(Labels.getLabel(msg.label().key()));
        }

        if (ft.equals(FellowType.NEW)) {
            Executions.sendRedirect(url);
        } else if (ft.equals(FellowType.SELF)) {
            ((Include) Path.getComponent(componentPath)).setSrc(url);
        } else if (ft.equals(FellowType.POPUP)) {
            ((Window) self).detach();
        }
    }
}
