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
package mx.dr.forms.controller;

import java.util.ArrayList;
import java.util.List;
import mx.dr.forms.constants.FellowType;
import mx.dr.forms.constants.FormActions;
import mx.dr.forms.dto.GenericDtoIN;
import mx.dr.forms.dto.GenericDtoOneIN;
import mx.dr.forms.view.utils.DtoConverter;
import mx.dr.forms.view.utils.ValidateForm;
import mx.dr.forms.view.DRFellowLink;
import mx.dr.forms.view.DRRootEntity;
import mx.dr.forms.view.component.DRCaptcha;
import mx.dr.forms.view.utils.DRFormBuilder;
import mx.dr.forms.view.utils.DRGeneralViewUtils;
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
import org.zkoss.zul.Window;

/**
 * Abstract class that defines the behavior of the view based on the model class from view and the type of action requested.
 * </br></br>
 * Clase abstracta que determina el comportamiento de la vista basandose en la clase del modelo de la vista y el tipo de accion solicitada.
 * @version 1.0
 * @author Jorge Luis Martinez Ramirez
 * @since 13/08/2012
 */
public abstract class DRGenericControllerAbstract extends GenericForwardComposer {
    /**
     * Class that models the behavior of the light is passed as a parameter when invoking the screen.
     * <br/>
	* Clase que modela el comportamiento de la vista se pasa como parametro al momento de invocar la pantalla.
	**/
    protected Class dtoClass;
	/**
	 * Name of the action that defines the behavior of the screen.
	 * <br/>
	* Nombre de la accion que define el comportamiento de la pantalla.
	**/
    protected String action;
	/**
	* Parameter value to identify which record to update or read.
	* <br/> 
	* Valor del parametro para identificar que registro se va a actualizar o leer.
	**/
    protected String param;
    /**
	* Method that operates after composing the view here is used to dynamically build the view.
	* Metodo que opera despues de componer la vista, aqui se usa para construir de forma dinamica la vista.
	* @see org.zkoss.ui.util.GenericForwardComposer#doAfterCompose
	**/
    public void doAfterCompose(Component comp) throws Exception {

        super.doAfterCompose(comp);
        String dtoClassName = Executions.getCurrent().getParameter("dto_class") == null ? (String) Executions.getCurrent().getArg().get("dto_class") : Executions.getCurrent().getParameter("dto_class");
        action = Executions.getCurrent().getParameter("action") == null ? (String) Executions.getCurrent().getArg().get("action") : Executions.getCurrent().getParameter("action");

        System.out.println(dtoClassName);
        System.out.println(action);

        dtoClass = Class.forName(dtoClassName);


        Object dto = null;
        Object bo = null;
        if (action.equals(FormActions.EDIT.name())) {

            DRFellowLink drAction = DRGeneralViewUtils.readAnnotation(dtoClass, FormActions.EDIT);
            System.out.println("inicio");

            param = Executions.getCurrent().getParameter(drAction.param()) == null ? (String) Executions.getCurrent().getArg().get(drAction.param()) : Executions.getCurrent().getParameter(drAction.param());
            bo = findSuchObject(param, dtoClass, drAction);
            System.out.println(param);
            dto = dtoClass.newInstance();
            DtoConverter.buildDTO(bo, dto);
            System.out.println(dto);
        } else if (action.equals(FormActions.READ.name())) {

            DRFellowLink drRead = DRGeneralViewUtils.readAnnotation(dtoClass, FormActions.READ);
            System.out.println("inicio");

            System.out.println("readparam");
            param = Executions.getCurrent().getParameter(drRead.param()) == null ? (String) Executions.getCurrent().getArg().get(drRead.param()) : Executions.getCurrent().getParameter(drRead.param());
            bo = findSuchObject(param, dtoClass, drRead);
            System.out.println(param);
            dto = dtoClass.newInstance();
            DtoConverter.buildDTO(bo, dto);
            System.out.println(dto);
        }

        DRFormBuilder.buildForm(comp, dtoClass, dto, action, comp);
    }
    /**
     * Method searches the register required to read or update.
	* Metodo busca el registro requerido para leer o actualizar.
	* @param param Parameter value to find the desired record, usually a single value in the database / Valor del parametro para encontrar el registro deseado, generalmente es un valor unico en la base de datos.
	* @param dtoClass Class representing / Clase que representa 
	* @return Found object using the given parameter. Objeto encontrado mediante el parametro otorgado.
	**/
    protected Object findSuchObject(String param, Class dtoClass, DRFellowLink drAction) throws Exception {
        DRRootEntity drEntity = (DRRootEntity) dtoClass.getAnnotation(DRRootEntity.class);
        GenericDtoOneIN dtoIN = new GenericDtoOneIN();
        dtoIN.setAclass(drEntity.entity());
        dtoIN.setParam(param);

        String[] invokerArray = null;
        Object bo = null;
        if (drAction != null) {
            invokerArray = drAction.paramAction().split("@");

            Class modelInvoker = Class.forName(invokerArray[0]);
            Object facade = modelInvoker.newInstance();
            bo = modelInvoker.getMethod(invokerArray[1], GenericDtoOneIN.class).invoke(facade, dtoIN);
        }
        System.out.println(invokerArray);
        return bo;
    }

	/**
	* Generates a new word in the captcha
	*Genera una nueva palabra en el captcha
	*@param event Evento generado al oprimir el boton de recaptcha
	**/
    public void drReCaptcha(ForwardEvent event) {
        Component comp = event.getTarget();
        System.out.println(comp);
        String id = event.getOrigin().getTarget().getId();
        System.out.println(id);
        comp = comp.getFellow(id.replaceFirst(DRCaptcha.BUTTON_SUFIX, "") + DRCaptcha.FIELD_SUFIX);
        ((Captcha) comp).randomValue();
    }
    
	/**
	 * Method that executes a low generic search <code> onClick event </ code>, the <code> id </ code> Component (eg button) must be called <code> search </ code>, this assignment must done in <code> zul </ code> that will contain the component
	 * <br/>
	* Metodo que ejecuta una busqueda generica bajo el evento <code>onClick</code>, el <code>id</code> del Componente (ej. boton) debe ser llamado <code>search</code>, esta asignacion debe hacerse en el <code>zul</code> que contendra dicho componente.
	*
	**/
    public void onClick$search() {
        try {
            Object dto;
            dto = DRFormBuilder.fillDTO(action, dtoClass, self);
            List<WrongValueException> listErrors = new ArrayList<WrongValueException>();
            ValidateForm.validate(dto, self, listErrors, action);
            GenericDtoIN dtoIN = DtoConverter.convert(dto);
            DRFellowLink drAction = DRGeneralViewUtils.readAnnotation(dtoClass, FormActions.SEARCH);

            dtoIN.setSessionParams(DRGeneralViewUtils.buildMapSessionParams(drAction.sessionParams(), sessionScope));
            Listbox listComponent = (Listbox) self.getFellow(drAction.resultsComponent().id());
            DRGeneralViewUtils.fillListModel(drAction, listComponent, dtoIN, self);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WrongValueException(self, e.getMessage());
        }
    }
    /**
     * Method that implements a general call to action to create or update a record.
     * <br/>
	* Metodo que implementa un llamado general a la accion de crear o actualizar un registro.
	* @deprecated use <code>onClick$generalAction</code> 
	**/
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
                DRFellowLink drAddAction = DRGeneralViewUtils.readAnnotation(dtoClass, FormActions.ADD);


                String[] invokerArray = drAddAction.submitAction().split("@");
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
    /**
     * Method of action its main purpose is to validate fields capture and delivery to the programmer as its valued <code> dto</code> .
     * You should implement the <code> zul </code> under <code> generalAction id </code> in the desired component.
	* <br/>
	* Metodo de accion general su principal proposito es la validacion de los campos de captura y su entrega al programador en forma de su <code>dto<code/> valuado.
	* Se debe implementar en el <code>zul</code> bajo el <code>id generalAction</code> en el componente deseado.
	**/
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
    /**
     * Method to run a custom operation.
     * <br/>
	* Metodo para ejecutar una operacion personalizada.
	* @param o Objeto <code> DTO</code> valued with values ​​previously entered by the user and validated / <code>dto</code> valuado con los valores introducidos por el usuario previamente ya validados
	* @throws Exception If an error occurs / si ocurre algun error.
	**/
    protected abstract void doSomething(Object o) throws Exception;
}
