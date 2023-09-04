package com.lanuza.phildrinkswms.views.form;

import com.lanuza.phildrinkswms.data.entity.Product;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public class ProductForm extends FormLayout {


    TextField code = new TextField("Code");
    TextField product = new TextField("Product");
    TextField price = new TextField("Price");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Product> binder = new BeanValidationBinder<>(Product.class);


    public ProductForm(){
        addClassName("product-form");
        binder.bindInstanceFields(this);

        add(
                code,
                product,
                price,
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

    public void setProduct(Product product){
        binder.setBean(product);
    }

    //Events
    public static abstract class ProductFormEvent extends ComponentEvent<ProductForm>{

        private Product product;

        protected ProductFormEvent(ProductForm source, Product product){
            super(source, false);
            this.product = product;
        }

        public Product getProduct(){
            return product;
        }
    }

    public static class SaveEvent extends ProductFormEvent{
        SaveEvent(ProductForm source, Product product){
            super(source,product);
        }
    }

    public static class DeleteEvent extends ProductFormEvent{
        DeleteEvent(ProductForm source, Product product){
            super(source, product);

        }
    }

    public static class CloseEvent extends ProductFormEvent{
        CloseEvent(ProductForm source){
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
