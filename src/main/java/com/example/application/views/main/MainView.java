package com.example.application.views.main;

import com.example.application.views.CrudBasic;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Home")
@Route(value = "")
public class MainView extends Div {
//    Grid<Pessoa> grid = new Grid<>(Pessoa.class);
//    TextField filterText = new TextField();
//    SideNavLabelled sideNav = new SideNavLabelled();
//    AppLayoutNavbar appLayoutNavbar = new AppLayoutNavbar();
    private final Tab funcionario;
    private final Tab home;
    private final VerticalLayout content;
    CrudBasic crud = new CrudBasic();

    public MainView(){
//        addClassName("list-view");
//        setSizeFull();
////        configureForm();
//
//        add(getContent());
        home = new Tab("Home");
        funcionario = new Tab("Funcionarios");

        Tabs tabs = new Tabs();
        tabs.setAutoselect(false);
        tabs.setWidth("100%");
        tabs.setSizeFull();
        
        tabs.addSelectedChangeListener(event -> setContent(event.getSelectedTab()));
        tabs.add(home,funcionario);

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
        } else {
            content.add(new Paragraph("This is the Shipping tab"));
        }
    }

//    private Component getContent() {
//        HorizontalLayout content = new HorizontalLayout(appLayoutNavbar);
////        content.setFlexGrow(1, crud);
//        content.setFlexGrow(1, appLayoutNavbar);
//        content.addClassName("content");
//        content.setSizeFull();
//        return content;
//    }

//    private void configureForm() {
//        form = new ContactForm();
//        form.setWidth("25em");
//    }

//    private HorizontalLayout getToolbar() {
//        filterText.setPlaceholder("Filter by name...");
//        filterText.setClearButtonVisible(true);
//        filterText.setValueChangeMode(ValueChangeMode.LAZY);
//
//        Button addContactButton = new Button("Add contact");
//
//        var toolbar = new HorizontalLayout(filterText, addContactButton);
//        toolbar.addClassName("toolbar");
//        return toolbar;
//    }

}
