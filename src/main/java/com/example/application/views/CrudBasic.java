package com.example.application.views;

import com.example.application.component.data.FuncionarioDataProvider;
import com.example.application.component.data.DepartamentoDataProvider;
import com.example.application.dto.DepartamentoDTO;
import com.example.application.dto.FuncionarioDTO;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@PageTitle("Funcionarios")
@Route("funcionarios")
public class CrudBasic extends Div {
    FuncionarioDataProvider dataProvider = new FuncionarioDataProvider();
    static DepartamentoDataProvider departamentoDataProvider = new DepartamentoDataProvider();
    private Crud<FuncionarioDTO> crud;
    private String ID = "id";

    private String NOME = "nome";
    private String RG = "rg";
    private String CARGO = "cargo";
    private String SALARIO = "salario";
    private String DATA_NASCIMENTO = "dataNascimento";
    private String DATA_ADMISSAO = "dataAdmissao";
    private String STATUS = "status";
    private String DEPARTAMENTO = "departamento";
    private String EDIT_COLUMN = "vaadin-crud-edit-column";



    public CrudBasic() throws SQLException {
        crud = new Crud<>(FuncionarioDTO.class, createEditor());

        setupGrid();
        setupDataProvider();
        setupToolbar();

        add(crud);
    }

    private void setupDataProvider() {

        crud.setDataProvider(dataProvider);
        crud.addDeleteListener(deleteEvent ->{
            try {
                dataProvider.delete(deleteEvent.getItem());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        crud.addSaveListener(saveEvent -> {
            try {
                dataProvider.salvar(saveEvent.getItem());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setupGrid() {
        Grid<FuncionarioDTO> grid = crud.getGrid();

        grid.setMinWidth("98vw");
        grid.setHeight("800px");

        List<String> visibleColumns = Arrays.asList(ID,NOME,RG,CARGO,DEPARTAMENTO,SALARIO,DATA_NASCIMENTO,DATA_ADMISSAO,STATUS,EDIT_COLUMN);
        grid.getColumns().forEach(column -> {
            String key = column.getKey();
            if(!visibleColumns.contains(key)){
                grid.removeColumn(column);
            }
        });

        grid.setColumnOrder(grid.getColumnByKey(ID),
                grid.getColumnByKey(NOME),
                grid.getColumnByKey(RG),
                grid.getColumnByKey(CARGO),
                grid.getColumnByKey(DEPARTAMENTO),
                grid.getColumnByKey(SALARIO),
                grid.getColumnByKey(DATA_NASCIMENTO),
                grid.getColumnByKey(DATA_ADMISSAO),
                grid.getColumnByKey(STATUS),
                grid.getColumnByKey(EDIT_COLUMN));
    }

    private CrudEditor<FuncionarioDTO> createEditor() {
        TextField nome = new TextField("Nome");
        IntegerField rg = new IntegerField("Registro Geral");
        NumberField salario = new NumberField("Salario");
        TextField cargo = new TextField("Cargo");
        ComboBox<String> departamento = new ComboBox<>("Departamento");
        /***
         * Tentei setar um atrbuto para a minha entidade FuncionarioDTO que tinha o tipo DepartamentoDTO
         * mas acabou não dando muito certo de como eu poderia manipular isso ou pelo menos não deixou muito claro no
         * tutorial do Vaadin então eu adaptei para o nosso estilo brasileiro de gambiarra, veras alguns comentarios no
         * @Class(FuncionarioDAO.java)
         */
        departamento.setItems(getDepartamentos());
        DatePicker dataNascimento = new DatePicker("Data de Nascimento");
        DatePicker dataAdmissao = new DatePicker("Data de Admissão");
        TextField status = new TextField("Status");


        FormLayout form = new FormLayout(nome,rg,salario,departamento,cargo,dataNascimento,dataAdmissao,status);
        form.setColspan(departamento, 2);
        form.setMaxWidth("480px");
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("30em",2));

        Binder<FuncionarioDTO> binder =new Binder<>(FuncionarioDTO.class);
        binder.forField(nome).asRequired().bind(FuncionarioDTO::getNome, FuncionarioDTO::setNome);
        binder.forField(rg).asRequired().bind(FuncionarioDTO::getRg, FuncionarioDTO::setRg);
        binder.forField(salario).asRequired().bind(FuncionarioDTO::getSalario, FuncionarioDTO::setSalario);
        binder.forField(cargo).asRequired().bind(FuncionarioDTO::getCargo, FuncionarioDTO::setCargo);
        binder.forField(departamento).asRequired().bind(FuncionarioDTO::getDepartamento, FuncionarioDTO::setDepartamento);
        binder.forField(dataNascimento).asRequired().bind(FuncionarioDTO::getDataNascimento, FuncionarioDTO::setDataNascimento);
        binder.forField(dataAdmissao).asRequired().bind(FuncionarioDTO::getDataAdmissao, FuncionarioDTO::setDataAdmissao);
        binder.forField(status).asRequired().bind(FuncionarioDTO::getStatus, FuncionarioDTO::setStatus);

        return new BinderCrudEditor<>(binder,form);
    }

    private void setupToolbar() {
        Html total = new Html("<span>Quantidade de Funcionarios <b>" + dataProvider.DATABASE.size()
                + "</b> </span>");

        Button button = new Button("Novo Funcionario", VaadinIcon.PLUS.create());
        button.addClickListener(event -> {
            crud.edit(new FuncionarioDTO(), Crud.EditMode.NEW_ITEM);
        });
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        crud.setNewButton(button);

        HorizontalLayout toolbar = new HorizontalLayout(total);
        toolbar.setAlignItems(FlexComponent.Alignment.CENTER);
        toolbar.setFlexGrow(1, toolbar);
        toolbar.setSpacing(false);
        crud.setToolbar(toolbar);
    }

    private static List<String> getDepartamentos(){
        List<DepartamentoDTO> departamentoDTOList = departamentoDataProvider.DATABASE;
        List<String> departamentos = new ArrayList<>();
        for (DepartamentoDTO departamentoDTO : departamentoDTOList) {
            departamentos.add(departamentoDTO.getNome());
        }
        return departamentos;
    }
}
