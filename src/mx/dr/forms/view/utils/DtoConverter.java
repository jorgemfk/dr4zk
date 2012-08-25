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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.dr.forms.constants.DefaultValues;
import mx.dr.forms.dto.DRAttachMedia;
import mx.dr.forms.dto.DRMedia;
import mx.dr.forms.dto.GenericDtoIN;
import mx.dr.forms.dto.GenericDtoParamsIN;
import mx.dr.forms.view.DRAddField;
import mx.dr.forms.view.DRBOField;
import mx.dr.forms.view.DREditField;
import mx.dr.forms.view.DRListField;
import mx.dr.forms.view.DRModel;
import mx.dr.forms.view.DRReadField;
import mx.dr.forms.view.DRRootEntity;
import mx.dr.util.ReflectionUtils;

/**
 *
 * @author Jorge Luis Martinez
 */
public class DtoConverter {

    private DtoConverter() {
    }
    private static Map<String, List<Field>> FIELD_LIST_MAP = new HashMap<String, List<Field>>();
    private static Comparator FIELD_LIST_COMPARATOR = new Comparator<Field>() {

        public int compare(Field o1, Field o2) {
            DRListField addField1;
            DRListField addField2;
            o1.setAccessible(true);
            o2.setAccessible(true);
            addField1 = o1.getAnnotation(DRListField.class);
            addField2 = o2.getAnnotation(DRListField.class);
            return addField1.order() - addField2.order();
        }
    };

    public static List<Field> getListFields(Class dtoClass) {
        List<Field> results = FIELD_LIST_MAP.get(dtoClass.getName());
        if (results == null) {
            DRListField listField;
            results = new ArrayList<Field>();
            for (Field field : dtoClass.getDeclaredFields()) {
                field.setAccessible(true);
                listField = field.getAnnotation(DRListField.class);
                if (listField != null) {
                    results.add(field);
                }
            }
            Collections.sort(results, FIELD_LIST_COMPARATOR);
            FIELD_LIST_MAP.put(dtoClass.getName(), results);
        }
        return results;
    }

    public static GenericDtoIN convert(Object dto) throws Exception {
        GenericDtoIN result = new GenericDtoIN();
        result.setViewDTO(dto);
        List<Object> list = new ArrayList<Object>();
        result.setBos(list);
        buildBO(dto, list, null, false);
        return result;
    }

    public static List buildResults(List bos, Class dtoClass) throws Exception {
        List list = new ArrayList();
        Object dto;
        if (bos != null) {
            for (Object bo : bos) {
                dto = dtoClass.newInstance();
                buildDTO(bo, dto);
                list.add(dto);
            }
        }
        return list;
    }

    private static void setDtoValue(Object bo, Object dto, Field field, String fieldname, DRBOField drBoField) throws Exception {
        if (drBoField.isMedia()) {
            Object value = ReflectionUtils.genericGet(bo, fieldname);
            if (value != null) {
                if (value instanceof DRAttachMedia) {
                    field.set(dto, DRMediaUtils.buildMedia((DRAttachMedia) value));
                } else if (value instanceof Collection) {
                    field.set(dto, DRMediaUtils.buildMedias((Collection) value));
                } else {
                    System.out.println("value incorrect: " + value);
                }
            } else {
                field.set(dto, new ArrayList<DRMedia>());
            }
        } else {
            field.set(dto, ReflectionUtils.genericGet(bo, fieldname));
        }
    }

    public static void buildDTO(Object bo, Object dto) throws Exception {

        DRBOField drBOField;
        DRModel drModel;
        String[] fieldParams;

        DRReadField drReadField;
        DREditField drEditField;

        Object compositeInstance;
        for (Field field : dto.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            drBOField = field.getAnnotation(DRBOField.class);
            drModel = field.getAnnotation(DRModel.class);
            if (drBOField != null) {
                if (drBOField.field().equals(DefaultValues.NONE)) {
                    setDtoValue(bo, dto, field, field.getName(), drBOField);
                } else {
                    fieldParams = drBOField.field().split("\\.");
                    compositeInstance = bo;
                    for (int i = 0; i < fieldParams.length; i++) {
                        if (i == fieldParams.length - 1) {
                            setDtoValue(compositeInstance, dto, field, fieldParams[i], drBOField);
                        } else {
                            compositeInstance = ReflectionUtils.genericGet(compositeInstance, fieldParams[i]);
                            if (compositeInstance == null) {
                                break;
                            }
                        }
                    }
                }
            } else if (drModel != null) {
                String[] invokerArray = drModel.model().split("@");
                Class modelInvoker = Class.forName(invokerArray[0]);
                Object facade = modelInvoker.newInstance();
                Object results = null;
                //TODO los atributos del modelo no se cargan si en el dto los atributos son asignados despues del costructor del modelo
                if (drModel.modelParams().length > 0) {
                    GenericDtoParamsIN dtoParams = new GenericDtoParamsIN();
                    for (String s : drModel.modelParams()) {
                        dtoParams.getParams().put(s, ReflectionUtils.genericGet(dto, s));
                    }
                    results =  modelInvoker.getMethod(invokerArray[1], dtoParams.getClass()).invoke(facade, dtoParams);
                } else {
                    results = modelInvoker.getMethod(invokerArray[1]).invoke(facade);
                }
                field.set(dto, results);
            } else {
                drEditField = field.getAnnotation(DREditField.class);
                drReadField = field.getAnnotation(DRReadField.class);
                if ((drReadField != null && !drReadField.isField()) || (drEditField != null && !drEditField.isField())) {
                    compositeInstance = field.getType().newInstance();
                    field.set(dto, compositeInstance);
                    buildDTO(bo, compositeInstance);
                }
            }
        }
    }

    public static void buildBO(Object dto, List<Object> list, Object currentInstance, boolean forceCurrent) throws Exception {
        Object instance;
        DRRootEntity drRootEntity;

        drRootEntity = dto.getClass().getAnnotation(DRRootEntity.class);

        if (drRootEntity == null || forceCurrent) {
            instance = currentInstance;
        } else {
            instance = drRootEntity.entity().newInstance();
            list.add(instance);
        }

        DRAddField drAddField;
        DREditField drEditField;

        DRBOField drBOField;
        String[] fieldParams;

        Object compositeInstance;
        Object compositeTemp;
        Field compositeField;
        for (Field field : dto.getClass().getDeclaredFields()) {
            field.setAccessible(true);


            drBOField = field.getAnnotation(DRBOField.class);
            if (drBOField != null && !drBOField.isMedia()) {//TODO imepmentar para la media
                if (drBOField.field().equals(DefaultValues.NONE)) {
                    System.out.println(field.getName() + " " + field.getType());
                    ReflectionUtils.genericSet(instance, field.getName(), field.get(dto), field.getType());
                } else {
                    fieldParams = drBOField.field().split("\\.");
                    compositeInstance = instance;
                    for (int i = 0; i < fieldParams.length; i++) {
                        if (i == fieldParams.length - 1) {
                            ReflectionUtils.genericSet(compositeInstance, fieldParams[i], field.get(dto), field.getType());
                        } else {
                            compositeField = ReflectionUtils.getField(fieldParams[i], compositeInstance.getClass());
                            compositeTemp = compositeInstance;
                            compositeInstance = ReflectionUtils.genericGet(compositeInstance, fieldParams[i]);
                            if (compositeInstance == null) {
                                compositeInstance = compositeField.getType().newInstance();
                                compositeField.set(compositeTemp, compositeInstance);
                            }
                        }
                    }
                }
            } else {
                drAddField = field.getAnnotation(DRAddField.class);
                drEditField = field.getAnnotation(DREditField.class);
                if ((drAddField != null && !drAddField.isField()) || (drEditField != null && !drEditField.isField())) {
                    buildBO(field.get(dto), list, instance, forceCurrent);
                }
            }
        }
    }
}
