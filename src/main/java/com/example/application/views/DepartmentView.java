package com.example.application.views;

import com.example.application.component.data.DepartamentoDataProvider;
import com.example.application.dto.DepartamentoDTO;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.util.Arrays;
import java.util.List;

public class DepartmentView extends Div {

    private String DEPARTAMENTO_NOME = "nome";
    private String STATUS = "status";
    private Crud<DepartamentoDTO> crud;
    private DepartamentoDataProvider departamentoDataProvider;

    public DepartmentView() {
        crud = new Crud<>(DepartamentoDTO.class, createEditor());

        setupGrid();
        setupDataProvider();
        setupToolbar();

        add(crud);
    }

    private void setupToolbar() {
        Button button = new Button("Novo departamento", VaadinIcon.PLUS.create());
        button.addClickListener(event -> {
            crud.edit(new DepartamentoDTO(), Crud.EditMode.NEW_ITEM);
        });
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        crud.setNewButton(button);
    }

    private void setupDataProvider() {
        departamentoDataProvider = new DepartamentoDataProvider();
        crud.setDataProvider(departamentoDataProvider);
        crud.addDeleteListener(
                deleteEvent -> departamentoDataProvider.delete(deleteEvent.getItem()));
        crud.addSaveListener(
                saveEvent -> departamentoDataProvider.persist(saveEvent.getItem()));
    }

    private void setupGrid() {
        Grid<DepartamentoDTO> grid = crud.getGrid();

        // Only show these columns (all columns shown by default):
        List<String> visibleColumns = Arrays.asList(DEPARTAMENTO_NOME, STATUS);
        grid.getColumns().forEach(column -> {
            String key = column.getKey();
            if (!visibleColumns.contains(key)) {
                grid.removeColumn(column);
            }
        });

        // Reorder the columns (alphabetical by default)
        grid.setColumnOrder(grid.getColumnByKey(DEPARTAMENTO_NOME),
                grid.getColumnByKey(STATUS));

        grid.setMinWidth("96vw");
    }

    private CrudEditor<DepartamentoDTO> createEditor() {
        TextField nome = new TextField("Nome departamento");
        ComboBox<String> status = new ComboBox<>("Status");
        status.setItems(getStatus());

        FormLayout form = new FormLayout(nome, status);

        Binder<DepartamentoDTO> binder = new Binder<>(DepartamentoDTO.class);
        binder.forField(nome).asRequired().bind(DepartamentoDTO::getNome, DepartamentoDTO::setNome);
        binder.forField(status).asRequired().bind(DepartamentoDTO::getStatus, DepartamentoDTO::setStatus);

        return new BinderCrudEditor<>(binder, form);
    }

    private List<String> getStatus() {
        return List.of("Ativo", "Inativo");
    }
}
