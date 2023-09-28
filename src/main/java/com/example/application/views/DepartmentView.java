package com.example.application.views;

import com.example.application.component.data.DepartamentoDataProvider;
import com.example.application.dto.DepartamentoDTO;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

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
        add(grid);
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
