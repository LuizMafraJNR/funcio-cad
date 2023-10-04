package com.example.application.component.data;

import com.example.application.dao.DepartamentoDAO;
import com.example.application.dto.DepartamentoDTO;
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

public class DepartamentoDataProvider extends AbstractBackEndDataProvider<DepartamentoDTO, CrudFilter> {
    @Override
    protected Stream<DepartamentoDTO> fetchFromBackEnd(Query<DepartamentoDTO, CrudFilter> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();

        Stream<DepartamentoDTO> stream = DATABASE.stream();

        if (query.getFilter().isPresent()) {
            stream = stream.filter(predicate(query.getFilter().get()))
                    .sorted(comparator(query.getFilter().get()));
        }

        return stream.skip(offset).limit(limit);
    }

    @Override
    protected int sizeInBackEnd(Query<DepartamentoDTO, CrudFilter> query) {
        long count = fetchFromBackEnd(query).count();

        if (sizeChangeListener != null) {
            sizeChangeListener.accept(count);
        }

        return (int) count;
    }

    public final List<DepartamentoDTO> DATABASE;

    {
        try {
            DATABASE = new ArrayList<>(DepartamentoDAO.listarDepartamentos());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Consumer<Long> sizeChangeListener;




    private Predicate<DepartamentoDTO> predicate(CrudFilter filter) {
        return filter.getConstraints().entrySet().stream()
                .map(constraint -> (Predicate<DepartamentoDTO>) departamento -> {
                    try {
                        Object value = valueOf(constraint.getKey(), departamento);
                        return value != null && value.toString().toLowerCase()
                                .contains(constraint.getValue().toLowerCase());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }).reduce(Predicate::and).orElse(e -> true);
    }

    private static Object valueOf(String fieldName, DepartamentoDTO departamento) {
        try {
            Field field = DepartamentoDTO.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(departamento);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Comparator<DepartamentoDTO> comparator(CrudFilter filter) {
        return filter.getSortOrders().entrySet().stream()
                .map(sortClause -> {
                    try {
                        Comparator<DepartamentoDTO> comparator = Comparator.comparing(
                                departamento -> (Comparable) valueOf(sortClause.getKey(),
                                        departamento));
                        if (sortClause.getValue() == SortDirection.ASCENDING) {
                            comparator = comparator.reversed();
                        }
                        return comparator;
                    } catch (Exception e) {
                        return (Comparator<DepartamentoDTO>) (o1, o2) -> 0;
                    }
                }).reduce(Comparator::thenComparing).orElse((o1, o2) -> 0);
    }

    public void persist(DepartamentoDTO item) {
        if (item.getId() == null) {
            item.setId(DATABASE.stream().map(DepartamentoDTO::getId).max(naturalOrder())
                    .orElse(0) + 1);
        }

        final Optional<DepartamentoDTO> existingItem = find(item.getId());
        if (existingItem.isPresent()) {
            int position = DATABASE.indexOf(existingItem.get());
            DATABASE.remove(existingItem.get());
            DATABASE.add(position, item);
            DepartamentoDTO departamento;
            try {
                departamento = DepartamentoDAO.buscarDepartamento(item.getId());
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao tentar buscar o departamento");
            }
            try {
                DepartamentoDAO.atualizarDepartamento(departamento);
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao tentar atualizar o departamento");
            }
        } else {
            DATABASE.add(item);
            try {
                DepartamentoDAO.inserirDepartamento(item);
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao tentar inserir o departamento");
            }
        }

        DATABASE.clear();
        try {
            DATABASE.addAll(DepartamentoDAO.listarDepartamentos());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao tentar listar os departamentos");
        }
    }

    Optional<DepartamentoDTO> find(Integer id) {
        return DATABASE.stream().filter(entity -> entity.getId().equals(id))
                .findFirst();
    }

    public void delete(DepartamentoDTO item) {
        DATABASE.removeIf(entity -> entity.getId().equals(item.getId()));
        try {
            DepartamentoDAO.deletarDepartamento(item.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao tentar deletar o departamento");
        }
        DATABASE.clear();
        try {
            DATABASE.addAll(DepartamentoDAO.listarDepartamentos());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao tentar listar os departamentos");
        }
    }
}
