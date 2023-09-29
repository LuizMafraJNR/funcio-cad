package com.example.application.views;

import com.example.application.component.data.DepartamentoDataProvider;
import com.example.application.dto.DepartamentoDTO;
import com.example.application.dto.FuncionarioDTO;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.List;

public class DepartmentView extends Div {


    private DepartamentoDataProvider departamentoDataProvider = new DepartamentoDataProvider();
    public DepartmentView() {

        Grid<DepartamentoDTO> grid = new Grid<>(DepartamentoDTO.class, false);
        grid.addColumn(DepartamentoDTO::getNome).setHeader("Departamento");
        grid.addComponentColumn(departamentoDTO ->  createStatusIcon(departamentoDTO))
                .setTooltipGenerator(departamentoDTO -> departamentoDTO.isAtivo() ? "Ativo" : "Inativo")
                .setHeader("Status");

        List<DepartamentoDTO> departamentos = departamentoDataProvider.DATABASE;
        grid.setItems(departamentos);
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.setMinWidth("97vw");
        grid.setMinHeight("400px");

//        Button adicionaDepartamento = new Button("Adicionar Departamento", VaadinIcon.PLUS.create());
//        HorizontalLayout footer = new HorizontalLayout(adicionaDepartamento);
//        footer.setAlignItems(FlexComponent.Alignment.AUTO);
//        footer.getStyle().set("flex-wrap", "wrap");
//        footer.getThemeList().clear();


        Button button = new Button("Novo Departamento", VaadinIcon.PLUS.create());
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.setAlignItems(FlexComponent.Alignment.CENTER);
        toolbar.setFlexGrow(1, button);
        toolbar.setSpacing(false);

        add(grid,button);
    }

    private void setupToolbar() {


    }

    private Icon createStatusIcon(DepartamentoDTO departamentoDTO){
        Boolean isAtivo = departamentoDTO.isAtivo();
        Icon icon;
        if (isAtivo){
            icon = VaadinIcon.CHECK.create();
            icon.getElement().getThemeList().add("badge success");
        } else {
            icon = VaadinIcon.CLOSE_SMALL.create();
            icon.getElement().getThemeList().add("badge error");
        }
        icon.getStyle().set("padding", "var(--lumo-space-xs)");
        return icon;
    }
}
