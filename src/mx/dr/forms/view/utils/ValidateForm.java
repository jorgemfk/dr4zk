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

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import mx.dr.forms.constants.DefaultValues;
import mx.dr.forms.constants.FormActions;
import mx.dr.forms.dto.DRAttachMedia;
import mx.dr.forms.dto.DRMedia;
import mx.dr.forms.view.DRAddField;
import mx.dr.forms.view.DREditField;
import mx.dr.forms.view.component.DRAttachList;
import mx.dr.forms.view.component.DRCaptcha;
import mx.dr.forms.view.component.DRComboBox;
import mx.dr.forms.view.component.DRFCKEditor;
import mx.dr.forms.view.component.DRDateBox;
import mx.dr.forms.view.component.DRTextBox;
import mx.dr.forms.view.component.DRListBox;
import mx.dr.forms.view.component.DRSpinner;
import mx.dr.util.DRGeneralUtils;
import mx.dr.util.ReflectionUtils;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Captcha;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;


public class ValidateForm implements Serializable{
	/**
	 * 
	 */
        private ValidateForm() {}

        private static void evalComponent(Field field, boolean isRequiered,String labelKey, Object vo,Component comp, List<WrongValueException> listErrors, Object value) throws Exception{
            
            DRTextBox drInputText;
            DRDateBox drInputDate;
            DRListBox drListBox;
            DRFCKEditor drFCKEditor;
            DRCaptcha drCaptcha;
            DRAttachList drAttachList;
            DRComboBox drComboBox;
            DRSpinner drSpinner;

            drInputText = field.getAnnotation(DRTextBox.class);
            drInputDate = field.getAnnotation(DRDateBox.class);
            drListBox = field.getAnnotation(DRListBox.class);
            drFCKEditor = field.getAnnotation(DRFCKEditor.class);
            drCaptcha = field.getAnnotation(DRCaptcha.class);
            drAttachList = field.getAnnotation(DRAttachList.class);
            drComboBox = field.getAnnotation(DRComboBox.class);
            drSpinner = field.getAnnotation(DRSpinner.class);

            if (drInputText != null) {
                        if(isRequiered && (value == null || ((String)value).trim().equals(""))){
                           Object[] param={Labels.getLabel(labelKey)};
                           listErrors.add(new WrongValueException(comp.getFellow(field.getName()),Labels.getLabel("dr.forms.label.required",param)));
                           return;
                        }
                        if(drInputText.minlenght() > DefaultValues.NO_LIMIT && ((String)value).trim().length()<drInputText.minlenght()){
                           Object[] param={ Labels.getLabel(labelKey), drInputText.minlenght(),((String)value).trim().length() };
                           listErrors.add(new WrongValueException(comp.getFellow(field.getName()),Labels.getLabel("dr.forms.label.min.lenght",param)));
                           return;
                        }
                        if(!drInputText.pattern().equals(DefaultValues.NONE) && !((String)value).trim().equals("")&&!((String)value).matches(drInputText.pattern())){
                            Object[] param={ Labels.getLabel(labelKey)};
                            listErrors.add(new WrongValueException(comp.getFellow(field.getName()),Labels.getLabel("dr.forms.label.pattern",param)));
                            return;
			}
            } else if (drListBox != null) {
                        if(isRequiered && (value == null || (ReflectionUtils.genericGet(value, "id")).toString().equals("-1"))){
                           Object[] param={ Labels.getLabel(labelKey)};
                            listErrors.add(new WrongValueException(comp.getFellow(field.getName()),Labels.getLabel("dr.forms.label.required",param)));
                           return;
                        }
            } else if (drComboBox != null) {
                        if(isRequiered && (value == null || (ReflectionUtils.genericGet(value, "id")).toString().equals("-1"))){
                           Object[] param={ Labels.getLabel(labelKey)};
                            listErrors.add(new WrongValueException(comp.getFellow(field.getName()),Labels.getLabel("dr.forms.label.required",param)));
                           return;
                        }
           }else if (drSpinner!= null) {
                        if(isRequiered && value == null){
                            Object[] param={ Labels.getLabel(labelKey)};
                           listErrors.add(new WrongValueException(comp.getFellow(field.getName()),Labels.getLabel("dr.forms.label.required",param)));
                           return;
                        }
                        if(value!=null && ((Integer)value)<drSpinner.minValue()){
                           listErrors.add(new WrongValueException(comp.getFellow(field.getName()),Labels.getLabel("dr.forms.label.spinner.min",DRGeneralUtils.argsAsArray(Labels.getLabel(labelKey), drSpinner.minValue()))));
                           return;
                        }
            }else if (drInputDate != null) {
                        if(isRequiered && value == null){
                            Object[] param={ Labels.getLabel(labelKey)};
                           listErrors.add(new WrongValueException(comp.getFellow(field.getName()),Labels.getLabel("dr.forms.label.required",param)));
                           return;
                        }

                        if(value !=null && !drInputDate.minValueReference().equals(DefaultValues.NONE)){
                            Object refObj = ReflectionUtils.genericGet(vo, drInputDate.minValueReference());
                            if(refObj == null){
                                listErrors.add(new WrongValueException(comp.getFellow(field.getName()),Labels.getLabel("dr.forms.label.ref.null", DRGeneralUtils.argsAsArray(drInputDate.minValueReference()))));
                                return;
                            }
                            SimpleDateFormat sf = new SimpleDateFormat(drInputDate.format());
                            if(refObj instanceof Date && ((Date)value).before((Date)refObj)){
                                listErrors.add(new WrongValueException(comp.getFellow(field.getName()),Labels.getLabel("dr.forms.label.date.min", DRGeneralUtils.argsAsArray(  Labels.getLabel(labelKey) , sf.format((Date)refObj)))));
                                return;

                            }
                            if(refObj instanceof Integer ){
                                Date date = DRGeneralUtils.addDays((Integer)refObj, Calendar.getInstance().getTime());
                                if(((Date)value).before(date)){
                                     listErrors.add(new WrongValueException(comp.getFellow(field.getName()),Labels.getLabel("dr.forms.label.date.min", DRGeneralUtils.argsAsArray(Labels.getLabel(labelKey) , sf.format(date)))));
                                    return;
                                }
                            }
                        }
            } else if (drFCKEditor != null) {
                        if(isRequiered && (value == null || ((String)value).trim().equals(""))){
                            Object[] param={ Labels.getLabel(labelKey)};
                           listErrors.add(new WrongValueException(comp.getFellow(field.getName()),Labels.getLabel("dr.forms.label.required",param)));
                           return;
                        }
                        if(drFCKEditor.maxlenght() > DefaultValues.NO_LIMIT && ((String)value).trim().length()>drFCKEditor.maxlenght()){
                           Object[] param={ Labels.getLabel(labelKey), drFCKEditor.maxlenght(),((String)value).trim().length() };
                            listErrors.add(new WrongValueException(comp.getFellow(field.getName()),Labels.getLabel("dr.forms.label.maxlenght",param)));
                           return;
                        }
            } else if (drCaptcha != null) {
                        if(value == null || ((String)value).trim().equals("")){
                            Object[] param={ Labels.getLabel(labelKey)};
                           listErrors.add(new WrongValueException(comp.getFellow(field.getName()),Labels.getLabel("dr.forms.label.required",param)));
                           return;
                        }
                        if(!((String)value).trim().equalsIgnoreCase(((Captcha)comp.getFellow(field.getName()+DRCaptcha.FIELD_SUFIX)).getValue())){
                           Object[] param={ Labels.getLabel(labelKey)};
                            listErrors.add(new WrongValueException(comp.getFellow(field.getName()),Labels.getLabel("dr.forms.label.captcha",param)));
                           return;
                        }
            } else if (drAttachList != null) {
                    if(isRequiered && (value == null || ((Collection)value).isEmpty()) || ((DRMedia)((Collection)value).toArray()[0]).getNotUploaded()){
                            Object[] param={ Labels.getLabel(labelKey)};
                           listErrors.add(new WrongValueException(comp.getFellow(field.getName()),Labels.getLabel("dr.forms.label.required",param)));
                           return;
                        }

            }
        }
	public static void validate(Object vo,Component comp, List<WrongValueException> listErrors,String action) throws Exception{

                DRAddField vc;
                DREditField ec;
		Object value;


		for (Field field : vo.getClass().getDeclaredFields()) {
                        field.setAccessible(true);

			vc = field.getAnnotation(DRAddField.class);
                        ec = field.getAnnotation(DREditField.class);
                        if(vc!=null && action.equals(FormActions.ADD.name())){
                            value = field.get(vo);
                            if(!vc.isField()){
                                validate(value,comp,listErrors,action);
                            }else{
                                evalComponent(field, vc.required(),vc.label().key(), vo, comp, listErrors, value);
                            }
			/*if (vc.required()) {
				
				if (value == null || (value instanceof String && ((String)value).trim().equals("")) ) {

					if (!vc.dependOf().equals(DefaultValues.NONE)) {
						fieldA = vo.getClass().getField(vc.dependOf());
                                                fieldA.setAccessible(true);
                                                valueA = fieldA.get(vo);
						if (value == null && valueA == null) {
							sf.append(requerido);
							sf.append(": ");
							//if(prepend!=null){
							//	sf.append(prepend).append("-");
							//}
							sf.append(Labels.getLabel(vc.label().key()));
							sf.append(" � ").append(Labels.getLabel(fieldA.getAnnotation(DRAddField.class).label().key()));
							sf.append("@@");
						}
					} else {
						sf.append(requerido);
						sf.append(": ");
						//if(prepend!=null){
						//	sf.append(prepend).append("-");
							
						//}
						
						sf.append(Labels.getLabel(vc.label().key()));
						sf.append("@@");
                                                listErrors.add(new WrongValueException(comp.getFellow(field.getName()),"requerido: "+Labels.getLabel(vc.label().key())));
                                               
                                        }

				}
			}*/
                        
                    }else if(ec!=null && action.equals(FormActions.EDIT.name())){
                            value = field.get(vo);
                            if(!ec.isField()){
                                validate(value,comp,listErrors,action);
                            }else{
                                evalComponent(field, ec.required(),ec.label().key(), vo, comp, listErrors, value);
                            }
                    }
			/*if(!vc.fechaMenor().equals("none")){
				value = vc.fechaMenor().equals("now")?Calendar.getInstance().getTime(): vo.genericGet(vc.fechaMenor());
				valueA = vo.genericGet(vc.dependOf());
				if (value != null) {
					if(value instanceof Date && !((Date)value).after((Date)valueA)){
						sf.append(Labels.getLabel("general.msg.value.fecha", new Object[]{Labels.getLabel(vc.label()), df.format((Date)valueA)}));
						sf.append("@@");
					}
				}
			}
			*/
		}
	}

	public static Grid String2Label(String message) {
		Grid g = new Grid();
		g.getChildren().add(new Rows());
		Row r;
		List l = g.getRows().getChildren();
		for (String m : message.split("@@")) {
			r = new Row();
			r.getChildren().add(new Label(m));
			l.add(r);
		}
		return g;
	}
}
