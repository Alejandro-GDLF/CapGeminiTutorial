package com.ccsw.tutorial.client;

import org.springframework.data.jpa.domain.Specification;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.common.criteria.SearchCriteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ClientSpecification implements Specification<Client> {

    private static final long serialVersionUID = 1L;

    private final SearchCriteria criteria;

    public ClientSpecification(SearchCriteria criteria) {

        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(":") && criteria.getValue() != null) {
            Path<String> path = getPath(root);
            return builder.equal(path, criteria.getValue());
        }
        return null;
    }

    private Path<String> getPath(Root<Client> root) {
        String key = criteria.getKey();
        String[] split = key.split("[.]", 0);

        Path<String> expression = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]);
        }

        return expression;
    }

}
