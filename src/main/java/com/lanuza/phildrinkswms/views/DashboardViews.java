package com.lanuza.phildrinkswms.views;

import com.lanuza.phildrinkswms.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "",layout = MainLayout.class)
@PageTitle("dashboard | phildrinkswms")
public class DashboardViews extends VerticalLayout {

    DashboardViews(){
        addClassName("dashboard-views");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        H1 text = new H1("This is the dashboard");
        add(text);
    }

}
