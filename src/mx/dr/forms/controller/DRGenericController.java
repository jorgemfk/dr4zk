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
package mx.dr.forms.controller;

import java.util.ArrayList;
import java.util.List;
import mx.dr.forms.constants.FellowType;
import mx.dr.forms.constants.FormActions;
import mx.dr.forms.dto.GenericDtoIN;
import mx.dr.forms.dto.GenericDtoOneIN;
import mx.dr.forms.view.utils.DtoConverter;
import mx.dr.forms.view.utils.ValidateForm;
import mx.dr.forms.view.DRAddAction;
import mx.dr.forms.view.DREditAction;
import mx.dr.forms.view.DRMessage;
import mx.dr.forms.view.DRReadAction;
import mx.dr.forms.view.DRRootEntity;
import mx.dr.forms.view.DRSearchAction;
import mx.dr.forms.view.component.DRCaptcha;
import mx.dr.forms.view.utils.DRFormBuilder;
import mx.dr.forms.view.utils.DRGeneralViewUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.WrongValuesException;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Captcha;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author Jorge Luis Martinez
 */
public class DRGenericController extends GenericForwardComposer {

    protected Class dtoClass;
    protected String action;
    protected String param;

    public void doAfterCompose(Component comp) throws Exception {

        super.doAfterCompose(comp);
        String dtoClassName = Executions.getCurrent().getParameter("dto_class") == null ? (String) Executions.getCurrent().getArg().get("dto_class") : Executions.getCurrent().getParameter("dto_class");
        action = Executions.getCurrent().getParameter("action") == null ? (String) Executions.getCurrent().getArg().get("action") : Executions.getCurrent().getParameter("action");

        System.out.println(dtoClassName);
        System.out.println(action);

        dtoClass = Class.forName(dtoClassName);


        DREditAction drAction = (DREditAction) dtoClass.getAnnotation(DREditAction.class);
        DRReadAction drRead = (DRReadAction) dtoClass.getAnnotation(DRReadAction.class);
        System.out.println(drRead);
        Object dto = null;

        if ((drAction != null && action.equals(FormActions.EDIT.name())) || (drRead != null && action.equals(FormActions.READ.name()))) {
            System.out.println("inicio");
                param = null;
            if (drAction != null) {
                param = Executions.getCurrent().getParameter(drAction.param()) == null ? (String) Executions.getCurrent().getArg().get(drAction.param()) : Executions.getCurrent().getParameter(drAction.param());
            } else if (drRead != null) {
                System.out.println("readparam");
                param = Executions.getCurrent().getParameter(drRead.param()) == null ? (String) Executions.getCurrent().getArg().get(drRead.param()) : Executions.getCurrent().getParameter(drRead.param());
            }
            System.out.println(param);
            Object bo = findSuchObject(param, dtoClass, drAction, drRead);
            dto = dtoClass.newInstance();
            DtoConverter.buildDTO(bo, dto);
            System.out.println(dto);
        } else {
            System.out.println("nada ke hacer: " + ((drAction != null && action.equals(FormActions.EDIT.name())) || (drRead != null && action.equals(FormActions.READ.name()))) + " " + (drRead != null && action.equals(FormActions.READ.name())));
        }
        DRFormBuilder.buildForm(comp, dtoClass, dto, action, comp);
    }

    private Object findSuchObject(String param, Class dtoClass,DREditAction drAction,DRReadAction drRead) throws Exception{
        DRRootEntity drEntity = (DRRootEntity) dtoClass.getAnnotation(DRRootEntity.class);
            GenericDtoOneIN dtoIN = new GenericDtoOneIN();
            dtoIN.setAclass(drEntity.entity());
            dtoIN.setParam(param);

            String[] invokerArray = null;
            if (drAction != null) {
                invokerArray = drAction.paramAction().split("@");
            } else if (drRead != null) {
                invokerArray = drRead.paramAction().split("@");
            }
            System.out.println(invokerArray);
            Class modelInvoker = Class.forName(invokerArray[0]);
            Object facade = modelInvoker.newInstance();
            Object bo = modelInvoker.getMethod(invokerArray[1], GenericDtoOneIN.class).invoke(facade, dtoIN);
            return bo;
    }

    public void drReCaptcha(ForwardEvent event) {
        Component comp = event.getTarget();
        System.out.println(comp);
        String id = event.getOrigin().getTarget().getId();
        System.out.println(id);
        comp = comp.getFellow(id.replaceFirst(DRCaptcha.BUTTON_SUFIX, "") + DRCaptcha.FIELD_SUFIX);
        ((Captcha) comp).randomValue();
    }

    public void onClick$search() {
        try {
            Object dto;
            dto = DRFormBuilder.fillDTO(action, dtoClass, self);
            GenericDtoIN dtoIN = DtoConverter.convert(dto);
            DRSearchAction drAction = (DRSearchAction) dtoClass.getAnnotation(DRSearchAction.class);
            dtoIN.setSessionParams(DRGeneralViewUtils.buildMapSessionParams(drAction.sessionParams(), sessionScope));
            Listbox listComponent = (Listbox) self.getFellow(drAction.resultsComponent().id());
            DRGeneralViewUtils.fillListModel(drAction, listComponent, dtoIN, self);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WrongValueException(self, e.getMessage());
        }
    }

    public void onClick$save() throws WrongValuesException, WrongValueException {
        List<WrongValueException> listErrors;
        Object dto;
        try {
            dto = DRFormBuilder.fillDTO(action, dtoClass, self);
            listErrors = new ArrayList<WrongValueException>();
            ValidateForm.validate(dto, self, listErrors, action);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WrongValueException(self, e.getMessage());
        }
        if (listErrors.isEmpty()) {
            try {
                GenericDtoIN dtoIN = DtoConverter.convert(dto);
                DRAddAction drAddAction = (DRAddAction) dtoClass.getAnnotation(DRAddAction.class);

                String[] invokerArray = drAddAction.action().split("@");
                Class modelInvoker = Class.forName(invokerArray[0]);
                Object facade = modelInvoker.newInstance();
                modelInvoker.getMethod(invokerArray[1], GenericDtoIN.class).invoke(facade, dtoIN);


                if (drAddAction.fellow().equals(FellowType.NEW)) {
                    Executions.sendRedirect(drAddAction.url());
                } else if (drAddAction.fellow().equals(FellowType.SELF)) {
                    ((Include) Path.getComponent(drAddAction.componentPath())).setSrc(drAddAction.url());
                } else if (drAddAction.fellow().equals(FellowType.POPUP)) {
                    ((Window) self).detach();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new WrongValueException(self, e.getMessage());
            }

        } else {
            System.out.println(listErrors);
            WrongValueException[] errors = new WrongValueException[listErrors.size()];
            for (int i = 0; i < listErrors.size(); i++) {
                errors[i] = listErrors.get(i);
            }
            throw new WrongValuesException(errors);
        }


    }

    public void onClick$generalAction() throws WrongValuesException, WrongValueException {
        List<WrongValueException> listErrors;
        Object dto;
        try {
            dto = DRFormBuilder.fillDTO(action, dtoClass, self);
            listErrors = new ArrayList<WrongValueException>();
            ValidateForm.validate(dto, self, listErrors, action);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WrongValueException(self, e.getMessage());
        }
        if (listErrors.isEmpty()) {
            try {
                doSomething(dto);
            } catch (Exception e) {
                e.printStackTrace();
                throw new WrongValueException(self, e.getMessage());
            }

        } else {
            System.out.println(listErrors);
            WrongValueException[] errors = new WrongValueException[listErrors.size()];
            for (int i = 0; i < listErrors.size(); i++) {
                errors[i] = listErrors.get(i);
            }
            throw new WrongValuesException(errors);
        }


    }

    protected void doSomething(Object o) throws Exception {
        GenericDtoIN dtoIN = DtoConverter.convert(o);



        String[] invokerArray = null;
        FellowType ft = null;
        String url = null;
        String componentPath = null;
        DRMessage msg = null;
        if (action.equals(FormActions.ADD.name())) {
            DRAddAction drAddAction = (DRAddAction) dtoClass.getAnnotation(DRAddAction.class);
            invokerArray = drAddAction.action().split("@");
            ft = drAddAction.fellow();
            url = drAddAction.url();
            componentPath = drAddAction.componentPath();

            dtoIN.setSessionParams(DRGeneralViewUtils.buildMapSessionParams(drAddAction.sessionParams(), sessionScope));
            msg =  drAddAction.sucessMessage();
        } else if (action.equals(FormActions.EDIT.name())) {

            DREditAction drEditAction = (DREditAction) dtoClass.getAnnotation(DREditAction.class);
            Object bo = findSuchObject(param, dtoClass, drEditAction, null);
            //parece que hay rutinas repetidas solo funciona para un objeto
            //en anuncio y empresa service se repite esta rutina al editar
            DtoConverter.buildBO(o, null, bo, true);
            //TODO hacerlo generic for
            dtoIN.getBos().clear();
            dtoIN.getBos().add(bo);

            invokerArray = drEditAction.action().split("@");
            ft = drEditAction.fellow();
            url = drEditAction.url();
            componentPath = drEditAction.componentPath();
            dtoIN.setSessionParams(DRGeneralViewUtils.buildMapSessionParams(drEditAction.sessionParams(), sessionScope));
            msg =  drEditAction.sucessMessage();
        }
        Class modelInvoker = Class.forName(invokerArray[0]);
        Object facade = modelInvoker.newInstance();
        modelInvoker.getMethod(invokerArray[1], GenericDtoIN.class).invoke(facade, dtoIN);

        if(msg!=null && msg.label()!= null && msg.mold().equals(DRMessage.Mold.POPUP)){
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
