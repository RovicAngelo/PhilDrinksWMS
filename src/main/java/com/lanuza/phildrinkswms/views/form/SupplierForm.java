package com.lanuza.phildrinkswms.views.form;

import com.lanuza.phildrinkswms.data.entity.Supplier;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.awt.*;

public class SupplierForm extends FormLayout {


    TextField supplier = new TextField("Supplier");

    TextField address = new TextField("Address");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Supplier> binder = new BeanValidationBinder<>(Supplier.class);


    public SupplierForm(){
        addClassName("supplier-form");
        binder.bindInstanceFields(this);

        add(
                supplier,
                address,
                createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid()){
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    public void setSupplier(Supplier supplier){
        binder.setBean(supplier);
    }

    //Events
    public static abstract class SupplierFormEvent extends ComponentEvent<SupplierForm>{

        private Supplier supplier;

        protected SupplierFormEvent(SupplierForm source, Supplier supplier){
            super(source, false);
            this.supplier = supplier;
        }

        public Supplier getSupplier(){
            return supplier;
        }
    }

    public static class SaveEvent extends SupplierFormEvent{
        SaveEvent(SupplierForm source, Supplier supplier){
            super(source,supplier);
        }
    }

    public static class DeleteEvent extends SupplierFormEvent{
        DeleteEvent(SupplierForm source, Supplier supplier){
            super(source, supplier);
        }
    }

    public static class CloseEvent extends SupplierFormEvent{
        CloseEvent(SupplierForm source){
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener){
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent>listener){
        return addListener(SaveEvent.class, listener);
    }

    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener){
        return addListener(CloseEvent.class, listener);
    }
}
