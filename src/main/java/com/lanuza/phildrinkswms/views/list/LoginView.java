package com.lanuza.phildrinkswms.views.list;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.router.Route;

@Route("login-view")
public class LoginView extends Div {

    public LoginView(){
        addClassName("Login-View");
        LoginForm loginForm = new LoginForm();
        loginForm.getElement().getThemeList().add("dark");
        add(loginForm);
        loginForm.getElement().setAttribute("no-autofocus", "");
    }

    /*package com.vaadin.demo.component.login;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route("login-rich-content")
public class LoginRichContent extends Div {

    public LoginRichContent() {
        // tag::snippet[]
        // See login-rich-content.css
        addClassName("login-rich-content");

        LoginForm loginForm = new LoginForm();
        loginForm.getElement().getThemeList().add("dark");
        // end::snippet[]
        add(loginForm);
        // Prevent the example from stealing focus when browsing the
        // documentation
        loginForm.getElement().setAttribute("no-autofocus", "");
    }

}
*/
}
