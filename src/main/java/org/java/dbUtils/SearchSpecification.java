package org.java.dbUtils;

import lombok.AllArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.java.dbUtils.SearchOperation.*;


@AllArgsConstructor
public class SearchSpecification {
    private List<SearchCriteria> searchCriteriaList;

    public Predicate[] toPredicate(Root root, CriteriaBuilder builder) {
        return searchCriteriaList.stream()
                .map(el -> getPredicate(el, builder, root))
                .toArray(Predicate[]::new);
    }

    private Predicate getPredicate(SearchCriteria criteria, CriteriaBuilder builder, Root root) {
        switch (criteria.getOperation()) {
            case EQUALITY:
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            case NEGATION:
                return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
            case GREATER_THAN:
                return builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN:
                return builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LIKE:
                return builder.like(root.get(criteria.getKey()), criteria.getValue().toString());
            case STARTS_WITH:
                return builder.like(root.get(criteria.getKey()), criteria.getValue() + "%");
            case ENDS_WITH:
                return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue());
            case CONTAINS:
                return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            case OR:
                return builder.or(criteria.getSearchCriteriaList().stream()
                        .map(el -> getPredicate(el, builder, root))
                        .toArray(Predicate[]::new));
            default:
                throw new IllegalArgumentException(String.format(
                        "Operation `%s` not supported", criteria.getOperation()));
        }
    }
}
