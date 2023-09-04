package com.lanuza.phildrinkswms;

import com.lanuza.phildrinkswms.views.DashboardViews;
import com.lanuza.phildrinkswms.views.list.ProductListViews;
import com.lanuza.phildrinkswms.views.list.SupplierListViews;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {

    MainLayout(){
        createHeader();
        createNavigationDrawer();
    }

    private void createHeader(){
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        H1 logo = new H1("Phildrinks");
        logo.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM
        );

        Button toggleButton = new Button(VaadinIcon.LIGHTBULB.create(), click -> {
            ThemeList themeList = UI.getCurrent().getElement().getThemeList();

            if (themeList.contains(Lumo.DARK)) {
                themeList.remove(Lumo.DARK);
            } else {
                themeList.add(Lumo.DARK);
            }
        });
        Button logoutBtn = new Button("Logout");
        logoutBtn.addClickListener(e -> System.exit(0));
        var header = new HorizontalLayout(logo,toggleButton, logoutBtn);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM
        );

        addToNavbar(toggle,header);
    }

    private void createNavigationDrawer(){
        SideNav nav = new SideNav();

        SideNavItem dashboardLink = new SideNavItem("Dashboard", DashboardViews.class, VaadinIcon.DASHBOARD.create());
        SideNavItem productLink = new SideNavItem("Product", ProductListViews.class, VaadinIcon.PACKAGE.create());
        SideNavItem supplierLink = new SideNavItem("Supplier", SupplierListViews.class, VaadinIcon.TRUCK.create());

        nav.addItem(dashboardLink, productLink,supplierLink);

        Div navWrapper = new Div(nav);
        nav.setWidthFull();

        addToDrawer(navWrapper);
    }

}
