package com.lanuza.phildrinkswms.views.list;

import com.lanuza.phildrinkswms.MainLayout;
import com.lanuza.phildrinkswms.data.entity.Product;
import com.lanuza.phildrinkswms.data.service.phildrinksService;
import com.lanuza.phildrinkswms.views.form.ProductForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
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
@PageTitle("product | phildrinkswms")
@Route(value = "product", layout = MainLayout.class)
public class ProductListViews extends VerticalLayout {

    Grid<Product> productGrid = new Grid<>(Product.class);
    TextField filterText = new TextField();
    ProductForm form;
    phildrinksService service;
    public ProductListViews(phildrinksService service){
        this.service = service;
        addClassName("product-views");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(),getContent());

        updateList();
        closeEditor();
    }
    private void configureForm() {
        form = new ProductForm();
        form.setWidth("25em");
        form.addSaveListener(this::saveProduct);
        form.addDeleteListener(this::deleteProduct);
        form.addCloseListener(e -> closeEditor());
    }
    private HorizontalLayout getContent(){
        HorizontalLayout content = new HorizontalLayout(productGrid, form);
        content.setFlexGrow(2, productGrid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void saveProduct(ProductForm.SaveEvent event){
        service.saveProduct(event.getProduct());
        updateList();
        closeEditor();
    }

    private void deleteProduct(ProductForm.DeleteEvent event){
        service.deleteProduct(event.getProduct());
        updateList();
        closeEditor();


    }

    private void configureGrid(){
        productGrid.addClassName("product-grid");
        productGrid.setSizeFull();
        productGrid.setColumns("code", "product", "price");
        //productGrid.addColumn(Product::getCode).setHeader("code");
        //productGrid.addColumn(Product::getProduct).setHeader("product");
      //productGrid.addColumn(Product::getPrice).setHeader("price");
        productGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        productGrid.asSingleSelect().addValueChangeListener(event -> editProduct(event.getValue()));
    }

    private Component getToolbar(){
        filterText.setPlaceholder("Filter by product name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addProductBtn = new Button("Add Product");
        addProductBtn.addClickListener(e -> addProduct());

        var toolbar = new HorizontalLayout(filterText, addProductBtn);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void editProduct(Product product) {
        if(product ==  null){
            closeEditor();
        }else{
            form.setProduct(product);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setProduct(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addProduct() {
        productGrid.asSingleSelect().clear();
        editProduct(new Product());
    }

    private void updateList() {
        productGrid.setItems(service.findAllProducts(filterText.getValue()));
    }


}
