package org.java.dbUtils;

import lombok.Getter;

import java.util.List;

@Getter
public class SearchCriteria {
    private String key;
    private SearchOperation operation;
    private Object value;
    private List<SearchCriteria> searchCriteriaList;

    public SearchCriteria(String key, SearchOperation operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public SearchCriteria(SearchOperation operation, List<SearchCriteria> searchCriteriaList) {
        this.operation = operation;
        this.searchCriteriaList = searchCriteriaList;
    }
}
