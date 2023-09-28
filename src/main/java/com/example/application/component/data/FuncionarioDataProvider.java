package com.example.application.component.data;

import com.example.application.dao.FuncionarioDAO;
import com.example.application.dto.FuncionarioDTO;
import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Comparator.naturalOrder;

public class FuncionarioDataProvider extends AbstractBackEndDataProvider<FuncionarioDTO, CrudFilter> {

    public final List<FuncionarioDTO> DATABASE;

    {
        try {
            DATABASE = new ArrayList<>(FuncionarioDAO.listarFuncionarios());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Consumer<Long> sizeChangeListener;

    @Override
    protected Stream<FuncionarioDTO> fetchFromBackEnd(Query<FuncionarioDTO, CrudFilter> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();

        Stream<FuncionarioDTO> stream = DATABASE.stream();

        if (query.getFilter().isPresent()) {
            stream = stream.filter(predicate(query.getFilter().get()))
                    .sorted(comparator(query.getFilter().get()));
        }

        return stream.skip(offset).limit(limit);
    }

    @Override
    protected int sizeInBackEnd(Query<FuncionarioDTO, CrudFilter> query) {
        long count = fetchFromBackEnd(query).count();

        if (sizeChangeListener != null) {
            sizeChangeListener.accept(count);
        }

        return (int) count;
    }

    void setSizeChangeListener(Consumer<Long> listener) {
        sizeChangeListener = listener;
    }

    private static Predicate<FuncionarioDTO> predicate(CrudFilter crudFilter){
        return crudFilter.getConstraints().entrySet().stream()
                .map(contraint -> (Predicate<FuncionarioDTO>) funcionarioDTO -> {
                    try {
                        Object value = valueOf(contraint.getKey(), funcionarioDTO);
                        return value != null && value.toString().toLowerCase()
                                .contains(contraint.getValue().toLowerCase());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }).reduce(Predicate::and).orElse(e -> true);
    }

    private static Comparator<FuncionarioDTO> comparator(CrudFilter filter) {
        return filter.getSortOrders().entrySet().stream().map(sortClause -> {
            try {
                Comparator<FuncionarioDTO> comparator = Comparator.comparing(
                        person -> (Comparable) valueOf(sortClause.getKey(),
                                person));

                if (sortClause.getValue() == SortDirection.DESCENDING) {
                    comparator = comparator.reversed();
                }

                return comparator;

            } catch (Exception ex) {
                return (Comparator<FuncionarioDTO>) (o1, o2) -> 0;
            }
        }).reduce(Comparator::thenComparing).orElse((o1, o2) -> 0);
    }

    private static Object valueOf(String fieldName, FuncionarioDTO funcionario) {
        try {
            Field field = FuncionarioDTO.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(funcionario);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void salvar(FuncionarioDTO funcionarioDTO) throws SQLException {
        if (funcionarioDTO.getId() == null) {
            funcionarioDTO.setId(DATABASE.stream().map(FuncionarioDTO::getId).max(naturalOrder())
                    .orElse(0) + 1);
        }

        final Optional<FuncionarioDTO> existingItem = find(funcionarioDTO.getId());
        if (existingItem.isPresent()) {
            FuncionarioDTO revealFuncionario = FuncionarioDAO.buscarFuncionarioPorId(existingItem.get().getId(), funcionarioDTO);
            FuncionarioDAO.atualizaFuncionario(revealFuncionario);
        } else {
            DATABASE.add(funcionarioDTO);
            FuncionarioDAO.adicionarFuncionario(funcionarioDTO);
        }
        DATABASE.clear();
        DATABASE.addAll(FuncionarioDAO.listarFuncionarios());
    }

    public void delete(FuncionarioDTO funcionarioDTO) throws SQLException {
        FuncionarioDAO.excluirFuncionario(funcionarioDTO.getId());
        DATABASE.clear();
        DATABASE.addAll(FuncionarioDAO.listarFuncionarios());
    }

    Optional<FuncionarioDTO> find(Integer id) {
        return DATABASE.stream().filter(entity -> entity.getId().equals(id))
                .findFirst();
    }
}
