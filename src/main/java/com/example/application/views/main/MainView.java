package com.example.application.views.main;

import com.example.application.views.CrudBasic;
import com.example.application.views.DepartmentView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.sql.SQLException;

@PageTitle("Home")
@Route(value = "")
public class MainView extends Div {
    private final Tab funcionario;
    private final Tab home;
    private final Tab departamento;
    private final VerticalLayout content;
    CrudBasic crud = new CrudBasic();
    DepartmentView departmentView = new DepartmentView();

    public MainView() throws SQLException {

        home = new Tab("Home");
        funcionario = new Tab("Funcionarios");
        departamento = new Tab("Departamentos");

        Tabs tabs = new Tabs();
        tabs.setAutoselect(false);
        tabs.setWidth("100%");
        tabs.setSizeFull();
        
        tabs.addSelectedChangeListener(event -> setContent(event.getSelectedTab()));
        tabs.add(home,funcionario,departamento);

        content = new VerticalLayout();
        content.setSpacing(false);

        content.setWidth("100%");
        content.setSizeFull();
        content.setHeightFull();

        add(tabs,content);
    }

    private void setContent(Tab tab) {
        content.removeAll();
        if (tab == null) {
            return;
        }
        if (tab.equals(home)) {
            content.add("dasddas");
        } else if (tab.equals(funcionario)) {
            content.add(crud);
        } else if (tab.equals(departamento)) {
            content.add(departmentView);
        }
        else {
            content.add(new Paragraph("This is the Shipping tab"));
        }
    }
}
