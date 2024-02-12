package com.ccsw.tutorial.gameloan;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.gameloan.model.GameLoan;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class GameLoanSpecification implements Specification<GameLoan> {

    private static final long serialVersionUID = 1L;

    private final SearchCriteria criteria;

    public GameLoanSpecification(SearchCriteria criteria) {

        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<GameLoan> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getValue() == null)
            return null;

        Path<String> path = getPath(root);
        if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (path.getJavaType() == String.class) {
                return builder.like(path, "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(path, criteria.getValue());
            }
        } else if (criteria.getOperation().equalsIgnoreCase(">=")) {
            return builder.greaterThanOrEqualTo(root.<LocalDateTime>get(criteria.getKey()),
                    (LocalDateTime) criteria.getValue());

        } else if (criteria.getOperation().equalsIgnoreCase("<=")) {
            return builder.lessThanOrEqualTo(root.<LocalDateTime>get(criteria.getKey()),
                    (LocalDateTime) criteria.getValue());
        }
        return null;
    }

    private Path<String> getPath(Root<GameLoan> root) {
        String key = criteria.getKey();
        String[] split = key.split("[.]", 0);

        Path<String> expression = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]);
        }

        return expression;
    }

}