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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.dr.forms.constants.FormActions;
import mx.dr.forms.dto.DRAttachMedia;
import mx.dr.forms.dto.DRFieldDto;
import mx.dr.forms.dto.DRMedia;
import mx.dr.forms.dto.GenericDtoIN;
import mx.dr.forms.dto.GenericDtoParamsIN;
import mx.dr.forms.view.DRField;
import mx.dr.forms.view.DRIsMedia;
import mx.dr.forms.view.DRModel;
import mx.dr.forms.view.DRRootEntity;
import mx.dr.util.ReflectionUtils;

/**
 *
 * <br/>
 * Contruye las diferentes salidas de objeto.
 * @author Jorge Luis Martinez Ramirez
 * @version 1.0
 * @since 13/08/2011
 * @since v0.5
 */
public class DtoConverter {

    private static final String TOKEN = "$";
    private static final String STOKEN = "\\$";

    private DtoConverter() {
    }
    private static Map<String, List<DRFieldDto>> FIELD_LIST_MAP = new HashMap<String, List<DRFieldDto>>();
    private static Comparator FIELD_LIST_COMPARATOR = new DRFieldComparator(FormActions.LIST);

    public static List<DRFieldDto> getListFields(Class dtoClass) {
        List<DRFieldDto> results = FIELD_LIST_MAP.get(dtoClass.getName());
        if (results == null) {
            DRField listField;
            results = new ArrayList<DRFieldDto>();
            for (Field field : dtoClass.getDeclaredFields()) {
                field.setAccessible(true);
                listField = DRGeneralViewUtils.readAnnotation(field, FormActions.LIST);
                if (listField != null) {
                    results.add(new DRFieldDto(field, listField));
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

    private static void setDtoValue(Object bo, Object dto, Field field, String fieldname) throws Exception {
        if (field.getAnnotation(DRIsMedia.class) == null) {
            try {
                field.set(dto, ReflectionUtils.genericGet(bo, fieldname));
            } catch (NoSuchMethodException e) {
                System.err.println("THIS METHOD HASNT GET: " + e.getMessage());
            }
        } else {
            Object value = null;
            try {
                value = ReflectionUtils.genericGet(bo, fieldname);
            } catch (NoSuchMethodException e) {
                System.err.println("THIS METHOD HASNT GET: " + e.getMessage());
            }
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
        }
    }

    public static void buildDTO(Object bo, Object dto) throws Exception {

        DRModel drModel;
        String[] fieldParams;

        DRField drReadField;
        DRField drEditField;

        Object compositeInstance;
        for (Field field : dto.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            drModel = field.getAnnotation(DRModel.class);
            drEditField = DRGeneralViewUtils.readAnnotation(field, FormActions.EDIT);
            drReadField = DRGeneralViewUtils.readAnnotation(field, FormActions.READ);
            if ((drReadField != null && !drReadField.isField()) || (drEditField != null && !drEditField.isField())) {
                compositeInstance = field.getType().newInstance();
                field.set(dto, compositeInstance);
                buildDTO(bo, compositeInstance);
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
                    results = modelInvoker.getMethod(invokerArray[1], dtoParams.getClass()).invoke(facade, dtoParams);
                } else {
                    results = modelInvoker.getMethod(invokerArray[1]).invoke(facade);
                }
                field.set(dto, results);
            } else {
                if (!field.getName().contains(TOKEN)) {
                    setDtoValue(bo, dto, field, field.getName());
                } else {
                    fieldParams = field.getName().split(STOKEN);
                    compositeInstance = bo;
                    for (int i = 0; i < fieldParams.length; i++) {
                        if (i == fieldParams.length - 1) {
                            setDtoValue(compositeInstance, dto, field, fieldParams[i]);
                        } else {
                            try {
                                compositeInstance = ReflectionUtils.genericGet(compositeInstance, fieldParams[i]);
                            } catch (NoSuchMethodException e) {
                                System.err.println("THIS METHOD HASNT GETTED: " + e.getMessage());
                                break;
                            }
                            if (compositeInstance == null) {
                                break;
                            }
                        }
                    }
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
        
        if(instance == null){
            return;
        }

        DRField drAddField;
        DRField drEditField;

        String[] fieldParams;

        Object compositeInstance;
        Object compositeTemp;
        Field compositeField;
        for (Field field : dto.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            if (field.getAnnotation(DRIsMedia.class) == null) {//TODO imepmentar para la media
                if (!field.getName().contains(TOKEN)) {
                    System.out.println(field.getName() + " " + field.getType());
                    try {
                        ReflectionUtils.genericSet(instance, field.getName(), field.get(dto));
                    } catch (NoSuchMethodException e) {
                        System.err.println("THIS METHOD HASNT SETTED: " + e.getMessage());
                    }
                } else {
                    fieldParams = field.getName().split(STOKEN);
                    compositeInstance = instance;
                    for (int i = 0; i < fieldParams.length; i++) {
                        if (i == fieldParams.length - 1) {
                            try {
                                ReflectionUtils.genericSet(compositeInstance, fieldParams[i], field.get(dto));
                            } catch (NoSuchMethodException e) {
                                System.err.println("THIS METHOD HASNT SETTED: " + e.getMessage());
                            }
                        } else {
                            compositeField = ReflectionUtils.getField(fieldParams[i], compositeInstance.getClass());
                            compositeTemp = compositeInstance;
                            try {
                                compositeInstance = ReflectionUtils.genericGet(compositeInstance, fieldParams[i]);
                            } catch (NoSuchMethodException e) {

                                System.err.println("THIS METHOD HASNT GETTED: " + e.getMessage());
                                break;
                            }
                            if (compositeInstance == null) {
                                compositeInstance = compositeField.getType().newInstance();
                                compositeField.set(compositeTemp, compositeInstance);
                            }
                        }
                    }
                }
            } else {
                drAddField = DRGeneralViewUtils.readAnnotation(field, FormActions.ADD);
                drEditField = DRGeneralViewUtils.readAnnotation(field, FormActions.EDIT);
                if ((drAddField != null && !drAddField.isField()) || (drEditField != null && !drEditField.isField())) {
                    buildBO(field.get(dto), list, instance, forceCurrent);
                }
            }
        }
    }
}
