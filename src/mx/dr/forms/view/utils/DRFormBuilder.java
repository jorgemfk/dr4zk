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

import mx.dr.forms.dto.DRMedia;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.dr.forms.constants.DefaultValues;
import mx.dr.forms.constants.FormActions;
import mx.dr.forms.dto.DRAttachMedia;
import mx.dr.forms.dto.DRSupportViewDTO;
import mx.dr.forms.dto.GenericDtoIN;
import mx.dr.forms.dto.GenericDtoParamsIN;
import mx.dr.forms.view.DRAddField;
import mx.dr.forms.view.DREditField;
import mx.dr.forms.view.DRFellowLink;
import mx.dr.forms.view.DRListColumn;
import mx.dr.forms.view.DRListField;
import mx.dr.forms.view.DRReadField;
import mx.dr.forms.view.DRSearchAction;
import mx.dr.forms.view.DRSearchField;
import mx.dr.forms.view.component.DRAttachList;
import mx.dr.forms.view.component.DRCaptcha;
import mx.dr.forms.view.component.DRComboBox;
import mx.dr.forms.view.component.DRFCKEditor;
import mx.dr.forms.view.component.DRGmaps;
import mx.dr.forms.view.component.DRGrid;
import mx.dr.forms.view.component.DRGroupBox;
import mx.dr.forms.view.component.DRImage;
import mx.dr.forms.view.component.DRDateBox;
import mx.dr.forms.view.component.DRDecimalBox;
import mx.dr.forms.view.component.DRHtml;
import mx.dr.forms.view.component.DRIntBox;
import mx.dr.forms.view.component.DRTextBox;
import mx.dr.forms.view.component.DRLabel;
import mx.dr.forms.view.component.DRListBox;
import mx.dr.forms.view.component.DRSpinner;
import mx.dr.forms.view.component.DRTabBox;
import mx.dr.forms.view.component.DRTabPanel;
import mx.dr.forms.view.component.DRTimer;
import mx.dr.forms.zul.DRGenericListModel;
import mx.dr.forms.zul.DRLimitedListModel;
import mx.dr.util.DRGeneralUtils;
import mx.dr.util.ReflectionUtils;
import org.zkforge.fckez.FCKeditor;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.util.resource.Labels;
import org.zkoss.zhtml.Div;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.WrongValuesException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Attributes;

import org.zkoss.zul.Button;
import org.zkoss.zul.Captcha;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Html;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timer;

/**
 *
 * @author JLMR
 */
public class DRFormBuilder {

    private static Map<String, List<Field>> ADD_MAP = new HashMap<String, List<Field>>();
    private static Comparator ADD_COMPARATOR = new Comparator<Field>() {

        public int compare(Field o1, Field o2) {
            DRAddField addField1;
            DRAddField addField2;
            o1.setAccessible(true);
            o2.setAccessible(true);
            addField1 = o1.getAnnotation(DRAddField.class);
            addField2 = o2.getAnnotation(DRAddField.class);
            return addField1.order() - addField2.order();
        }
    };
    private static Map<String, List<Field>> EDIT_MAP = new HashMap<String, List<Field>>();
    private static Comparator EDIT_COMPARATOR = new Comparator<Field>() {

        public int compare(Field o1, Field o2) {
            DREditField editField1;
            DREditField editField2;
            o1.setAccessible(true);
            o2.setAccessible(true);
            editField1 = o1.getAnnotation(DREditField.class);
            editField2 = o2.getAnnotation(DREditField.class);
            return editField1.order() - editField2.order();
        }
    };
    private static Map<String, List<Field>> SEARCH_MAP = new HashMap<String, List<Field>>();
    private static Comparator SEARCH_COMPARATOR = new Comparator<Field>() {

        public int compare(Field o1, Field o2) {
            DRSearchField searchField1;
            DRSearchField searchField2;
            o1.setAccessible(true);
            o2.setAccessible(true);
            searchField1 = o1.getAnnotation(DRSearchField.class);
            searchField2 = o2.getAnnotation(DRSearchField.class);
            return searchField1.order() - searchField2.order();
        }
    };
    private static Map<String, List<Field>> READ_MAP = new HashMap<String, List<Field>>();

    private DRFormBuilder() {
    }

    public static Object fillDTO(String action, Class dtoClass, Component self) throws Exception {
        System.out.println(self);

        Object dto = dtoClass.newInstance();
        DRTextBox drInputText;
        DRIntBox drInputInt;
        DRDecimalBox drInputDecimal;
        DRDateBox drInputDate;
        DRListBox drListBox;
        DRFCKEditor drFCKEditor;
        DRAttachList drAttachList;
        DRSpinner drSpinner;
        DRComboBox drCombobox;
        DRImage drImage;
        DRGmaps drGmaps;

        List<Field> fields = null;
        if (action.equals(FormActions.ADD.name())) {
            fields = getAddFields(dtoClass);
        } else if (action.equals(FormActions.EDIT.name())) {
            fields = getEditFields(dtoClass);
        } else if (action.equals(FormActions.SEARCH.name())) {
            fields = getSearchFields(dtoClass);
        }
        Component comp;

        for (Field field : fields) {
            field.setAccessible(true);

            drInputText = field.getAnnotation(DRTextBox.class);
            drInputDate = field.getAnnotation(DRDateBox.class);
            drListBox = field.getAnnotation(DRListBox.class);
            drFCKEditor = field.getAnnotation(DRFCKEditor.class);
            drInputInt = field.getAnnotation(DRIntBox.class);
            drInputDecimal = field.getAnnotation(DRDecimalBox.class);
            drAttachList = field.getAnnotation(DRAttachList.class);
            drImage = field.getAnnotation(DRImage.class);
            drGmaps = field.getAnnotation(DRGmaps.class);
            drSpinner = field.getAnnotation(DRSpinner.class);
            drCombobox = field.getAnnotation(DRComboBox.class);
            if (drInputText != null) {
                comp = self.getFellow(field.getName());
                field.set(dto, ((Textbox) comp).getValue());
            } else if (drInputInt != null) {
                comp = self.getFellow(field.getName());
                field.set(dto, ((Intbox) comp).getValue());
            } else if (drInputDecimal != null) {
                comp = self.getFellow(field.getName());
                if (field.getType().equals(Double.class)) {
                    field.set(dto, ((Decimalbox) comp).getValue() == null ? null : ((Decimalbox) comp).getValue().doubleValue());
                } else {
                    field.set(dto, ((Decimalbox) comp).getValue());
                }

            } else if (drListBox != null) {
                comp = self.getFellow(field.getName());
                Listbox list = (Listbox) comp;
                //TODO mejorar, se selecciona el 1er elemento
                if (list.getSelectedIndex() < 0) {
                    list.setSelectedIndex(0);
                }
                field.set(dto, ((DRGenericListModel) list.getModel()).getElementAt(list.getSelectedIndex()));
            } else if (drInputDate != null) {
                comp = self.getFellow(field.getName());
                field.set(dto, ((Datebox) comp).getValue());
            } else if (drCombobox != null) {
                comp = self.getFellow(field.getName());
                if(((Combobox) comp).getSelectedIndex()!=-1){
                field.set(dto, ((DRGenericListModel) ((Combobox) comp).getModel()).getElementAt(((Combobox) comp).getSelectedIndex()));
                }else{
                    String  newValue = ((Combobox) comp).getValue();
                    if (!newValue.trim().equals("")) {
                        String[] invokerArray = drCombobox.action().split("@");
                        Class actionInvoker = Class.forName(invokerArray[0]);
                        Object facade = actionInvoker.newInstance();
                        Object created = actionInvoker.getMethod(invokerArray[1], String.class).invoke(facade, newValue);
                        field.set(dto, created);
                    }
                }
            } else if (drSpinner != null) {
                comp = self.getFellow(field.getName());
                field.set(dto, ((Spinner) comp).getValue());
            } else if (drFCKEditor != null) {
                comp = self.getFellow(field.getName());
                field.set(dto, ((FCKeditor) comp).getValue());
            } else if (drAttachList != null) {
                comp = self.getFellow(field.getName());
                field.set(dto, ((DRGenericListModel) ((Listbox) comp).getModel()).getResults());
            } else if (drImage != null) {
                //TODO
            } else if (drGmaps != null) {
                //TODO
            } else {
                field.set(dto, fillDTO(action, field.getType(), self));
            }


        }

        return dto;
    }

    public static void buildForm(Component comp, Class dtoClass, Object dtoValue, String action, Component origin) throws Exception {

        DRSupportViewDTO dtoSup;

        DRGrid drGrid;
        DRTabBox drTabBox;
        Grid grid = null;
        Row arow = null;
        Tabbox tabbox = null;
        if (action.equals(FormActions.ADD.name())) {
            DRAddField addField = null;
            drGrid = (DRGrid) dtoClass.getAnnotation(DRGrid.class);
            drTabBox = (DRTabBox) dtoClass.getAnnotation(DRTabBox.class);

            int cols = 0;
            int itCols = 1;

            if (drGrid != null) {
                grid = new Grid();
                grid.setId(drGrid.id());
                grid.setWidth(drGrid.width());
                cols = drGrid.cols();
                arow = new Row();
                grid.getChildren().add(new Rows());
                comp.getChildren().add(0, grid);

            } else if (drTabBox != null) {
                tabbox = new Tabbox();
                tabbox.setWidth(drTabBox.width());
                tabbox.getChildren().add(new Tabs());
                tabbox.getChildren().add(new Tabpanels());
                comp.getChildren().add(0, tabbox);
            } else if (drTabBox == null && drGrid == null && comp instanceof Grid) {
                grid = (Grid) comp;
                cols = drGrid.cols();
                arow = new Row();
            }

            for (Field field : getAddFields(dtoClass)) {
                field.setAccessible(true);

                addField = field.getAnnotation(DRAddField.class);
                dtoSup = buildOneElement(arow, cols, itCols, field, comp, dtoValue, action, origin, grid, tabbox, addField, null, null);
                itCols = dtoSup.getCont();
                arow = dtoSup.getArow();
            }
        } else if (action.equals(FormActions.EDIT.name())) {
            DREditField editField = null;
            drGrid = (DRGrid) dtoClass.getAnnotation(DRGrid.class);
            drTabBox = (DRTabBox) dtoClass.getAnnotation(DRTabBox.class);

            int cols = 0;
            int itCols = 1;

            if (drGrid != null) {
                grid = new Grid();
                grid.setId(drGrid.id());
                grid.setWidth(drGrid.width());
                cols = drGrid.cols();
                arow = new Row();
                grid.getChildren().add(new Rows());
                comp.getChildren().add(0, grid);

            } else if (drTabBox != null) {
                tabbox = new Tabbox();
                tabbox.setWidth(drTabBox.width());
                tabbox.getChildren().add(new Tabs());
                tabbox.getChildren().add(new Tabpanels());
                comp.getChildren().add(0, tabbox);
            } else if (drTabBox == null && drGrid == null && comp instanceof Grid) {
                grid = (Grid) comp;
                cols = drGrid.cols();
                arow = new Row();
            }

            for (Field field : getEditFields(dtoClass)) {
                field.setAccessible(true);

                editField = field.getAnnotation(DREditField.class);
                dtoSup = buildOneElement(arow, cols, itCols, field, comp, dtoValue, action, origin, grid, tabbox, null, editField, null);
                itCols = dtoSup.getCont();
                arow = dtoSup.getArow();
            }
        } else if (action.equals(FormActions.SEARCH.name())) {
            DRSearchField searchField = null;
            drGrid = (DRGrid) dtoClass.getAnnotation(DRGrid.class);
            drTabBox = (DRTabBox) dtoClass.getAnnotation(DRTabBox.class);

            int cols = 0;
            int itCols = 1;

            if (drGrid != null) {
                grid = new Grid();
                grid.setId(drGrid.id());
                grid.setWidth(drGrid.width());
                cols = drGrid.cols();
                arow = new Row();
                grid.getChildren().add(new Rows());
                comp.getChildren().add(0, grid);

            } else if (drTabBox != null) {
                tabbox = new Tabbox();
                tabbox.setWidth(drTabBox.width());
                tabbox.getChildren().add(new Tabs());
                tabbox.getChildren().add(new Tabpanels());
                comp.getChildren().add(0, tabbox);
            } else if (drTabBox == null && drGrid == null && comp instanceof Grid) {
                grid = (Grid) comp;
                cols = drGrid.cols();
                arow = new Row();
            }

            for (Field field : getSearchFields(dtoClass)) {
                field.setAccessible(true);

                searchField = field.getAnnotation(DRSearchField.class);
                dtoSup = buildOneElement(arow, cols, itCols, field, comp, dtoValue, action, origin, grid, tabbox, null, null, searchField);
                itCols = dtoSup.getCont();
                arow = dtoSup.getArow();
            }
            DRSearchAction drSearchAction = (DRSearchAction) dtoClass.getAnnotation(DRSearchAction.class);
            if (drSearchAction != null) {

                Listbox alist = addListBox(drSearchAction.resultsComponent(), origin, null, dtoValue, origin);

                if (drSearchAction.loadOnInit()) {
                    GenericDtoIN dtoIn = new GenericDtoIN();
                    dtoIn.setSessionParams(DRGeneralViewUtils.buildMapSessionParams(drSearchAction.sessionParams(),
                            origin.getDesktop().getSession().getAttributes()));
                    DRGeneralViewUtils.fillListModel(drSearchAction, alist, dtoIn, origin);
                }
            }
        } else if (action.equals(FormActions.READ.name())) {
            DRReadField drReadField;
            DRLabel drLabel;
            DRImage drImage;
            DRGmaps dRGmaps;
            DRTextBox drTextBox;
            DRListBox drListBox;
            DRHtml drHtml;
            DRTimer drTimer;
            System.out.println("redaction");
            Component parent = null;
            for (Field field : getReadFields(dtoClass)) {
                field.setAccessible(true);
                System.out.println(field.getName());
                drReadField = field.getAnnotation(DRReadField.class);
                parent = origin.getFellow(drReadField.parent());
                if (!drReadField.label().key().equals(DRLabel.NO_LABEL)) {
                    addLabel(drReadField.label(), parent);
                }
                drLabel = field.getAnnotation(DRLabel.class);
                drImage = field.getAnnotation(DRImage.class);
                dRGmaps = field.getAnnotation(DRGmaps.class);
                drHtml = field.getAnnotation(DRHtml.class);
                drListBox = field.getAnnotation(DRListBox.class);
                drTimer = field.getAnnotation(DRTimer.class);
                drTextBox = field.getAnnotation(DRTextBox.class);
                if (drLabel != null) {
                    addReadOnlyLabel(drLabel, parent, field, dtoValue);
                } else if (drImage != null) {
                    addImage(drImage, parent, field, dtoValue);
                } else if (drHtml != null) {
                    addHtml(drHtml, parent, field, dtoValue);
                } else if (dRGmaps != null) {
                    addGmaps(dRGmaps, parent, field, dtoValue);
                } else if (drListBox != null) {
                    addListBox(drListBox, parent, field, dtoValue, origin);
                } else if (drTimer != null) {
                    addTimer(drTimer, parent, field, dtoValue);
                } else if (drTextBox != null) {
                    addInputText(drTextBox, parent, field, dtoValue);
                } else {
                    buildForm(comp, field.getType(), dtoValue == null ? null : field.get(dtoValue), action, origin);
                }

            }

        }
    }

    private static DRSupportViewDTO buildOneElement(Row arow, final int cols, int itCols, final Field field, final Component comp, final Object dtoValue, final String action, final Component origin, final Grid grid, final Tabbox tabbox, final DRAddField addField, final DREditField editField, final DRSearchField searchField) throws Exception {

        DRTextBox drInputText;
        DRDateBox drInputDate;
        DRIntBox drInputInt;
        DRDecimalBox drInputDecimal;
        DRListBox drListBox;
        DRAttachList drAttachListBox;
        DRFCKEditor drFCKEditor;
        DRCaptcha drCaptcha;
        DRLabel drLabel;
        DRSpinner drSpinner;
        DRComboBox drComboBox;

        DRTabPanel drTabPanel;
        DRGroupBox drGroupBox;
        drInputText = field.getAnnotation(DRTextBox.class);
        drInputDecimal = field.getAnnotation(DRDecimalBox.class);
        drInputInt = field.getAnnotation(DRIntBox.class);
        drInputDate = field.getAnnotation(DRDateBox.class);
        drTabPanel = field.getAnnotation(DRTabPanel.class);
        drListBox = field.getAnnotation(DRListBox.class);
        drFCKEditor = field.getAnnotation(DRFCKEditor.class);
        drCaptcha = field.getAnnotation(DRCaptcha.class);
        drGroupBox = field.getAnnotation(DRGroupBox.class);
        drLabel = field.getAnnotation(DRLabel.class);
        drAttachListBox = field.getAnnotation(DRAttachList.class);
        drSpinner = field.getAnnotation(DRSpinner.class);
        drComboBox = field.getAnnotation(DRComboBox.class);

        if (drInputText != null) {

            if (addField != null) {
                addLabel(addField.label(), arow);
            } else if (editField != null) {
                addLabel(editField.label(), arow);
            } else if (searchField != null) {
                addLabel(searchField.label(), arow);
            }
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);
            addInputText(drInputText, arow, field, dtoValue);
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);

        } else if (drComboBox != null) {

            if (addField != null) {
                addLabel(addField.label(), arow);
            } else if (editField != null) {
                addLabel(editField.label(), arow);
            } else if (searchField != null) {
                addLabel(searchField.label(), arow);
            }
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);
            addComboBox(drComboBox, arow, field, dtoValue);
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);

        } else if (drSpinner != null) {

            if (addField != null) {
                addLabel(addField.label(), arow);
            } else if (editField != null) {
                addLabel(editField.label(), arow);
            } else if (searchField != null) {
                addLabel(searchField.label(), arow);
            }
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);
            addSpinner(drSpinner, arow, field, dtoValue);
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);

        } else if (drInputInt != null) {

            if (addField != null) {
                addLabel(addField.label(), arow);
            } else if (editField != null) {
                addLabel(editField.label(), arow);
            } else if (searchField != null) {
                addLabel(searchField.label(), arow);
            }
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);
            addInputInt(drInputInt, arow, field, dtoValue);
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);

        } else if (drInputDecimal != null) {

            if (addField != null) {
                addLabel(addField.label(), arow);
            } else if (editField != null) {
                addLabel(editField.label(), arow);
            } else if (searchField != null) {
                addLabel(searchField.label(), arow);
            }
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);
            addInputDecimal(drInputDecimal, arow, field, dtoValue);
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);

        } else if (drAttachListBox != null) {

            if (addField != null) {
                addLabel(addField.label(), arow);
            } else if (editField != null) {
                addLabel(editField.label(), arow);
            } else if (searchField != null) {
                addLabel(searchField.label(), arow);
            }
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);
            addAttachList(drAttachListBox, arow, field, dtoValue);
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);

        } else if (drListBox != null) {

            if (addField != null) {
                addLabel(addField.label(), arow);
            } else if (editField != null) {
                addLabel(editField.label(), arow);
            } else if (searchField != null) {
                addLabel(searchField.label(), arow);
            }
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);
            addListBox(drListBox, arow, field, dtoValue, origin);
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);

        } else if (drInputDate != null) {

            if (addField != null) {
                addLabel(addField.label(), arow);
            } else if (editField != null) {
                addLabel(editField.label(), arow);
            } else if (searchField != null) {
                addLabel(searchField.label(), arow);
            }
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);
            addInputDate(drInputDate, arow, field, dtoValue);
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);

        } else if (drFCKEditor != null) {
            if (addField != null) {
                addLabel(addField.label(), arow);
            } else if (editField != null) {
                addLabel(editField.label(), arow);
            } else if (searchField != null) {
                addLabel(searchField.label(), arow);
            }
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);
            addFCKEditor(drFCKEditor, arow, field, dtoValue);
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);

        } else if (drLabel != null) {
            if (addField != null) {
                addLabel(addField.label(), arow);
            } else if (editField != null) {
                addLabel(editField.label(), arow);
            } else if (searchField != null) {
                addLabel(searchField.label(), arow);
            }
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);
            addReadOnlyLabel(drLabel, arow, field, dtoValue);
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);

        } else if (drCaptcha != null) {
            if (addField != null) {
                addLabel(addField.label(), arow);
            } else if (editField != null) {
                addLabel(editField.label(), arow);
            } else if (searchField != null) {
                addLabel(searchField.label(), arow);
            }
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);
            addCaptcha(drCaptcha, arow, field, origin);
            arow = getRow(arow, grid.getRows(), itCols, cols);
            itCols = getCols(itCols, cols);

        } else if (drTabPanel != null) {
            if (addField != null) {
                tabbox.getTabs().appendChild(new Tab(Labels.getLabel(addField.label().key())));
            } else if (editField != null) {
                tabbox.getTabs().appendChild(new Tab(Labels.getLabel(editField.label().key())));
            } else if (searchField != null) {
                tabbox.getTabs().appendChild(new Tab(Labels.getLabel(searchField.label().key())));
            }
            Tabpanel atab = new Tabpanel();
            if (drGroupBox != null) {
                addGroupBox(drGroupBox, atab, field.getType(), action, origin, dtoValue == null ? null : field.get(dtoValue));
            } else {
                buildForm(atab, field.getType(), dtoValue == null ? null : field.get(dtoValue), action, origin);
            }
            tabbox.getTabpanels().appendChild(atab);
        } else if (drGroupBox != null) {
            if (grid != null) {
                addGroupBox(drGroupBox, grid, field.getType(), action, origin, dtoValue == null ? null : field.get(dtoValue));
            } else {
                addGroupBox(drGroupBox, comp, field.getType(), action, origin, dtoValue == null ? null : field.get(dtoValue));
            }
        } else {
            buildForm(grid, field.getType(), dtoValue == null ? null : field.get(dtoValue), action, origin);
        }
        return new DRSupportViewDTO(arow, itCols);
    }

    private static List<Field> getAddFields(Class dtoClass) {
        List<Field> results = ADD_MAP.get(dtoClass.getName());
        if (results == null) {
            DRAddField addField;
            results = new ArrayList<Field>();
            for (Field field : dtoClass.getDeclaredFields()) {
                field.setAccessible(true);
                addField = field.getAnnotation(DRAddField.class);
                if (addField != null) {
                    results.add(field);
                }
            }
            Collections.sort(results, ADD_COMPARATOR);
            ADD_MAP.put(dtoClass.getName(), results);
        }
        return results;
    }

    private static List<Field> getEditFields(Class dtoClass) {
        List<Field> results = EDIT_MAP.get(dtoClass.getName());
        if (results == null) {
            DREditField editField;
            results = new ArrayList<Field>();
            for (Field field : dtoClass.getDeclaredFields()) {
                field.setAccessible(true);
                editField = field.getAnnotation(DREditField.class);
                if (editField != null) {
                    results.add(field);
                }
            }
            Collections.sort(results, EDIT_COMPARATOR);
            EDIT_MAP.put(dtoClass.getName(), results);
        }
        return results;
    }

    public static List<Field> getSearchFields(Class dtoClass) {
        List<Field> results = SEARCH_MAP.get(dtoClass.getName());
        if (results == null) {
            DRSearchField searchField;
            results = new ArrayList<Field>();
            for (Field field : dtoClass.getDeclaredFields()) {
                field.setAccessible(true);
                searchField = field.getAnnotation(DRSearchField.class);
                if (searchField != null) {
                    results.add(field);
                }
            }
            Collections.sort(results, SEARCH_COMPARATOR);
            SEARCH_MAP.put(dtoClass.getName(), results);
        }
        return results;
    }

    public static List<Field> getReadFields(Class dtoClass) {
        List<Field> results = READ_MAP.get(dtoClass.getName());
        if (results == null) {
            DRReadField readField;
            results = new ArrayList<Field>();
            for (Field field : dtoClass.getDeclaredFields()) {
                field.setAccessible(true);
                readField = field.getAnnotation(DRReadField.class);
                if (readField != null) {
                    results.add(field);
                }
            }
            READ_MAP.put(dtoClass.getName(), results);
        }
        return results;
    }

    private static Row getRow(Row arow, Component parent, int current, int limit) {
        parent.appendChild(arow);
        return current == limit ? new Row() : arow;
    }

    private static int getCols(int current, int limit) {
        return current == limit ? 1 : ++current;
    }

    private static void addLabel(DRLabel drLabel, Component arow) throws Exception {
        Label label = new Label(Labels.getLabel(drLabel.key()));
        if (!drLabel.zclass().equals(DefaultValues.NONE)) {
            label.setZclass(drLabel.zclass());
        }
        arow.appendChild(label);
    }

    private static void addReadOnlyLabel(DRLabel drLabel, Component arow, Field field, Object dtoValue) throws Exception {
        Object value = field.get(dtoValue);
        Label label = new Label(value != null ? value.toString() : " ");
        if (!drLabel.zclass().equals(DefaultValues.NONE)) {
            label.setZclass(drLabel.zclass());
        }
        arow.appendChild(label);
    }

    private static void addAttachList(DRAttachList drAttachList, Component arow, Field field, Object dtoValue) throws Exception {
        Listbox alist = new Listbox();
        alist.setWidth("400px");
        alist.setItemRenderer(drAttachList.itemRenderer().getName());
        alist.setId(field.getName());
        List<DRMedia> medias = null;
        if (dtoValue != null) {
            medias = (List<DRMedia>) field.get(dtoValue);
            if (medias.size() < drAttachList.maxRow()) {
                medias.add(new DRMedia());
            }
        } else {
            medias = new ArrayList<DRMedia>();
            medias.add(new DRMedia());
        }
        alist.setModel(new DRLimitedListModel(medias, drAttachList.maxRow()));
        arow.appendChild(alist);
    }

    private static Listbox addListBox(DRListBox drListBox, Component arow, Field field, Object dtoValue, Component parent) throws Exception {

        Listbox alist = new Listbox();
        alist.setMold(drListBox.mold());
        alist.setItemRenderer(drListBox.itemRenderer().getName());
        alist.setSclass(drListBox.sclass());
        if (!drListBox.id().equals(DefaultValues.NONE)) {
            alist.setId(drListBox.id());
            alist.setVisible(false);
        } else {
            alist.setId(field.getName());
        }
        if (drListBox.header()) {
            alist.appendChild(new Listhead());
            DRListField lField;
            Listheader head;
            Class listClass = drListBox.dtoResult();
            for (Field f : DtoConverter.getListFields(listClass)) {
                lField = f.getAnnotation(DRListField.class);
                head = new Listheader(Labels.getLabel(lField.label().key()));
                head.setWidth(lField.width());
                alist.getListhead().appendChild(head);
            }
            DRListColumn drListColumn = (DRListColumn) listClass.getAnnotation(DRListColumn.class);
            if (drListColumn != null) {
                for (DRFellowLink drFellow : drListColumn.columns()) {
                    head = new Listheader(Labels.getLabel(drFellow.label().key()));

                    alist.getListhead().appendChild(head);

                }
            }

        }
        List results = null;

        if (!drListBox.model().equals(DefaultValues.NONE)) {
            String[] invokerArray = drListBox.model().split("@");
            Class modelInvoker = Class.forName(invokerArray[0]);
            Object facade = modelInvoker.newInstance();
            
            if (drListBox.modelParams().length > 0) {
                GenericDtoParamsIN dtoParams = new GenericDtoParamsIN();
                for (String s : drListBox.modelParams()) {
                    if (s.equals(DRListBox.DESKTOP)) {
                        dtoParams.getParams().put(DRListBox.DESKTOP, parent.getDesktop());
                    } else {
                        if (dtoValue != null) {
                            dtoParams.getParams().put(s, ReflectionUtils.genericGet(dtoValue, s));
                        }
                    }
                }
                results = (List) modelInvoker.getMethod(invokerArray[1], dtoParams.getClass()).invoke(facade, dtoParams);
            } else {
                results = (List) modelInvoker.getMethod(invokerArray[1]).invoke(facade);
            }

            alist.setModel(new DRGenericListModel(results));
            if (dtoValue != null) {
                Object value = field.get(dtoValue);
                if (value != null) {
                    for (Object o : results) {
                        if (value.equals(o)) {
                            alist.setSelectedIndex(results.indexOf(o));
                        }
                    }
                }
            }
        }
        if(drListBox.mold().equals(DRListBox.LIST_PAGING_MOLD)){
        Label message = new Label(Labels.getLabel("dr.forms.results.empty"));
            message.setSclass("alert3");
            message.setId(new StringBuffer(alist.getId()).append("_msg").toString());
            arow.appendChild(message);
            message.setVisible(results==null?false:results.isEmpty());
            
        }
        //alist.setWidth("660px");
        arow.appendChild(alist);
        return alist;
    }

    private static void addInputText(DRTextBox drInputText, Component arow, Field field, Object dtoValue) throws Exception {

        Textbox atext = new Textbox();
        atext.setRows(drInputText.rows());
        atext.setType(drInputText.type().name());
        atext.setReadonly(drInputText.readOnly());
        atext.setCols(drInputText.cols());
        if(drInputText.uppercase()){atext.setStyle("text-transform:uppercase;");}

        if (drInputText.maxlenght() > DefaultValues.NO_LIMIT) {
            atext.setMaxlength(drInputText.maxlenght());
        }

        if (dtoValue != null) {
            atext.setValue((String) field.get(dtoValue));
        }
        atext.setId(field.getName());
        if(!drInputText.mask().equals(DefaultValues.NONE)){
            Div div =  new Div();
            arow.appendChild(div);
            arow = div;  
        }
        arow.appendChild(atext);
        if(!drInputText.mask().equals(DefaultValues.NONE)){
              arow.appendChild(new Html(
                    new StringBuffer()
                    .append("<script type=\"text/javascript\">")
                    .append("$('#")
                    .append(atext.getUuid())
                    .append("').mask('")
                    .append(drInputText.mask())
                    .append("');")
                    .append("</script>")
                    .toString()

                    ));
         }

    }

    private static void addInputInt(DRIntBox drInputInt, Component arow, Field field, Object dtoValue) throws Exception {

        Intbox atext = new Intbox();
        atext.setReadonly(drInputInt.readOnly());
        if (drInputInt.maxlenght() > DefaultValues.NO_LIMIT) {
            atext.setMaxlength(drInputInt.maxlenght());
        }

        if (dtoValue != null) {
            atext.setValue((Integer) field.getType().getMethod("intValue").invoke(field.get(dtoValue)));
        }
        atext.setId(field.getName());
        arow.appendChild(atext);
    }

    private static void addSpinner(DRSpinner drSpinner, Component arow, Field field, Object dtoValue) throws Exception {

        Spinner atext = new Spinner();
        if (drSpinner.maxlenght() > DefaultValues.NO_LIMIT) {
            atext.setMaxlength(drSpinner.maxlenght());
        }

        if (dtoValue != null) {
            atext.setValue((Integer) field.get(dtoValue));
        }
        atext.setId(field.getName());
        arow.appendChild(atext);
    }

    private static void addHtml(DRHtml drHtml, Component arow, Field field, Object dtoValue) throws Exception {

        Html atext = new Html();
        if (dtoValue != null) {
            atext.setContent((String) field.get(dtoValue));
        }
        atext.setId(field.getName());
        arow.appendChild(atext);
    }

    private static void addComboBox(DRComboBox drComboBox, Component arow, Field field, Object dtoValue) throws Exception {

        Combobox atext = new Combobox();

        String[] invokerArray = drComboBox.model().split("@");
        Class modelInvoker = Class.forName(invokerArray[0]);
        Object facade = modelInvoker.newInstance();
        List results = null;
        results = (List) modelInvoker.getMethod(invokerArray[1]).invoke(facade);
        atext.setModel(new DRGenericListModel(results));
        atext.setItemRenderer(drComboBox.itemRenderer().getName());
        if (dtoValue != null) {
            atext.setValue((String) field.get(dtoValue));
        }
        atext.setId(field.getName());
        arow.appendChild(atext);
    }

    private static void addInputDecimal(DRDecimalBox drInputDecimal, Component arow, Field field, Object dtoValue) throws Exception {

        Decimalbox atext = new Decimalbox();
        atext.setReadonly(drInputDecimal.readOnly());
        atext.setFormat(drInputDecimal.format());
        if (drInputDecimal.maxlenght() > DefaultValues.NO_LIMIT) {
            atext.setMaxlength(drInputDecimal.maxlenght());
        }

        if (dtoValue != null) {
            Object value = field.get(dtoValue);
            if (value != null && value instanceof BigDecimal) {
                atext.setValue((BigDecimal) field.get(dtoValue));
            } else if (value != null && value instanceof Double) {
                atext.setValue(BigDecimal.valueOf((Double) value));
            }
        }
        atext.setId(field.getName());
        arow.appendChild(atext);
    }

    private static void addInputDate(DRDateBox drInputDate, Component arow, Field field, Object dtoValue) throws Exception {

        Datebox atext = new Datebox();
        atext.setFormat(drInputDate.format());
        if (dtoValue != null) {
            atext.setValue((Date) field.get(dtoValue));
        }
        atext.setId(field.getName());
        arow.appendChild(atext);
    }

    private static void addFCKEditor(DRFCKEditor drFCKEditor, Component arow, Field field, Object dtoValue) throws Exception {
        FCKeditor atext = new FCKeditor();
        atext.setWidth(drFCKEditor.width());
        atext.setHeight(drFCKEditor.height());
        atext.setId(field.getName());
        if (dtoValue != null) {
            atext.setValue((String) field.get(dtoValue));
        }
        arow.appendChild(atext);
    }

    private static void addTimer(DRTimer drTimer, Component arow, Field field, Object dtoValue) throws Exception {
        Timer atext = new Timer(drTimer.delay());
        atext.setRepeats(drTimer.repeats());
        final Date end = (Date) field.get(dtoValue);
        final Label label = new Label();
        atext.addEventListener(Events.ON_TIMER, new EventListener() {

            public void onEvent(Event event) throws Exception {
                label.setValue(DRGeneralUtils.endTime(end));
            }
        });
        arow.appendChild(atext);
        arow.appendChild(label);
    }

    private static void addImage(final DRImage drImage, Component arow, Field field, Object dtoValue) throws Exception {
        Image atext = new Image();
        atext.setWidth(drImage.width());
        atext.setId(field.getName());
        if (dtoValue != null) {
            if (drImage.rotate()) {
                final List list = (List) field.get(dtoValue);
                if (list != null && !list.isEmpty()) {
                    Timer timer = new Timer(5000);
                    timer.setRepeats(true);
                    final Image img=atext;
                    timer.addEventListener(Events.ON_TIMER, new EventListener() {

                        private int current = 1;

                        public void onEvent(Event event) throws Exception {
                            if (current == list.size()) {
                                current = 0;
                            }
                            DRAttachMedia att = (DRAttachMedia) list.get(current);
                            img.setSrc(new StringBuffer(drImage.prepend()).append(att.getUri()).toString());
                            current++;
                        }
                    });
                    atext.setSrc(new StringBuffer(drImage.prepend()).append(((DRAttachMedia) list.get(0)).getUri()).toString());
                    arow.appendChild(timer);
                }else{
                    Label label = new Label(Labels.getLabel("dr.forms.msg.noimage"));
                    arow.appendChild(label);
                }
            } else {
                Object value = field.get(dtoValue);
                if (value instanceof Collection){
                    Grid grid = new Grid();
                    grid.appendChild(new Rows());
                    Row row= new Row();
                    grid.getRows().appendChild(row);
                    for(DRAttachMedia o :(Collection<DRAttachMedia>)value){
                        atext.setSrc(new StringBuffer(drImage.prepend()).append(o.getUri()).toString());
                        row.appendChild(atext);
                        atext = new Image();
                        atext.setWidth(drImage.width());
                    }
                    int min= drImage.minimages()-((Collection<DRAttachMedia>)value).size();
                    if(min>0){
                        for(int k=0; k<min;k++){
                         atext.setSrc(new StringBuffer(drImage.prepend()).append(drImage.nullimage()).toString());
                        row.appendChild(atext);
                        atext = new Image();
                        atext.setWidth(drImage.width());
                        }
                    }
                    arow.appendChild(grid);
                    return;
                }else{
                StringBuffer sf = new StringBuffer(drImage.prepend()).append((String) field.get(dtoValue));
                System.out.println(sf);
                atext.setSrc(sf.toString());
                if(!drImage.href().equals("")){
                    atext.setStyle("cursor: pointer;");
                    Object[] params =  new Object[drImage.hrefParams().length];
                    for(int i=0; i<drImage.hrefParams().length;i++){
                        params[i] =  ReflectionUtils.genericGet(dtoValue, drImage.hrefParams()[i]);
                    }
                    final String uri=Labels.getLabel(drImage.href(), params);

                    atext.addEventListener("onClick", new EventListener() {

                            public void onEvent(Event event) throws Exception {

                                Executions.sendRedirect(uri);
                            }
                        });
                }


                }
            }
        }
        arow.appendChild(atext);
    }

    private static void addGmaps(DRGmaps drGmaps, Component arow, Field field, Object dtoValue) throws Exception {
        Gmaps atext = new Gmaps();
        atext.setWidth(drGmaps.width());
        atext.setHeight(drGmaps.height());
        atext.setId(field.getName());
        atext.setShowLargeCtrl(drGmaps.showLargeCtrl());
        atext.setShowSmallCtrl(drGmaps.showSmallCtrl());
        if (dtoValue != null) {
            Double lat = (Double) ReflectionUtils.genericGet(dtoValue, drGmaps.latitude());
            Double lng = (Double) ReflectionUtils.genericGet(dtoValue, drGmaps.longitude());
            lat = lat == null? 0 : lat;
            lng = lng == null? 0 :lng;
            atext.setLat(lat);
            atext.setLng(lng);
            atext.appendChild(new Gmarker((String) ReflectionUtils.genericGet(dtoValue, drGmaps.gmarkContent()), lat, lng));
        }
        arow.appendChild(atext);
    }

    private static void addCaptcha(DRCaptcha drCaptcha, Component arow, Field field, Component comp) throws Exception {
        Grid agrid = new Grid();
        agrid.appendChild(new Rows());
        Row orow = new Row();

        Captcha atext = new Captcha();
        atext.setId(field.getName() + DRCaptcha.FIELD_SUFIX);
        Textbox text = new Textbox();
        text.setId(field.getName());

        Button abutton = new Button();
        abutton.setLabel(Labels.getLabel("dr.forms.label.regenerate"));
        abutton.setId(field.getName() + DRCaptcha.BUTTON_SUFIX);
        //abutton.addForward(Events.ON_CLICK, comp, "drReCaptcha");

        abutton.addEventListener(Events.ON_CLICK, new EventListener() {

            public void onEvent(Event event) throws Exception {
                Component comp = event.getTarget();
                String id = comp.getId();
                System.out.println(id);
                comp = comp.getFellow(id.replaceFirst(DRCaptcha.BUTTON_SUFIX, "") + DRCaptcha.FIELD_SUFIX);
                ((Captcha) comp).randomValue();
            }
        });


        orow.appendChild(text);
        agrid.getRows().appendChild(orow);
        orow = new Row();
        orow.appendChild(atext);
        agrid.getRows().appendChild(orow);
        orow = new Row();
        orow.appendChild(abutton);
        agrid.getRows().appendChild(orow);
        arow.appendChild(agrid);
        orow = new Row();
    }

    private static void addGroupBox(DRGroupBox drGroupBox, Component parent, Class dtoClass, String action, Component comp, Object dtoValue) throws Exception {
        Groupbox group = new Groupbox();
        group.setMold(drGroupBox.mold());
        group.setWidth(drGroupBox.width());
        group.setOpen(drGroupBox.open());
        buildForm(group, dtoClass, dtoValue, action, comp);

        if (!drGroupBox.label().key().equals(DRLabel.NO_LABEL)) {
            group.getChildren().add(0, new Caption(Labels.getLabel(drGroupBox.label().key())));
        }

        parent.getChildren().add(0, group);
    }
}
