package com.lanuza.phildrinkswms.views.list;

import com.lanuza.phildrinkswms.MainLayout;
import com.lanuza.phildrinkswms.data.entity.Supplier;
import com.lanuza.phildrinkswms.data.service.phildrinksService;
import com.lanuza.phildrinkswms.views.form.SupplierForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;


@SpringComponent
@Scope("prototype")
@PermitAll
@PageTitle("supplier | phildrinkswms")
@Route(value = "supplier", layout = MainLayout.class)
public class SupplierListViews extends VerticalLayout {

    Grid<Supplier> supplierGrid = new Grid<>(Supplier.class);
    TextField filterText = new TextField();
    SupplierForm form;
    phildrinksService service;
    public SupplierListViews(phildrinksService service){
        this.service = service;
        addClassName("supplier-views");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(),getContent());

        updateList();
        closeEditor();
    }
    private void configureForm() {
        form = new SupplierForm();
        form.setWidth("25em");
        form.addSaveListener(this::saveSupplier);
        form.addDeleteListener(this::deleteSupplier);
        form.addCloseListener(e -> closeEditor());
    }
    private HorizontalLayout getContent(){
        HorizontalLayout content = new HorizontalLayout(supplierGrid, form);
        content.setFlexGrow(2, supplierGrid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void saveSupplier(SupplierForm.SaveEvent event){
        service.saveSupplier(event.getSupplier());
        updateList();
        closeEditor();
    }

    private void deleteSupplier(SupplierForm.DeleteEvent event){
        service.deleteSupplier(event.getSupplier());
        updateList();
        closeEditor();
    }

    private void configureGrid(){
        supplierGrid.addClassName("supplier-grid");
        supplierGrid.setSizeFull();
        supplierGrid.setColumns("supplier","address");
        supplierGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        supplierGrid.asSingleSelect().addValueChangeListener(event -> editSupplier(event.getValue()));
    }

    private Component getToolbar(){
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addSupplierBtn = new Button("Add Supplier");
        addSupplierBtn.addClickListener(e -> addSupplier());

        var toolbar = new HorizontalLayout(filterText, addSupplierBtn);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void editSupplier(Supplier supplier) {
        if(supplier ==  null){
            closeEditor();
        }else{
            form.setSupplier(supplier);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setSupplier(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addSupplier() {
        supplierGrid.asSingleSelect().clear();
        editSupplier(new Supplier());
    }

    private void updateList() {
        supplierGrid.setItems(service.findAllSuppliers(filterText.getValue()));
    }


}
