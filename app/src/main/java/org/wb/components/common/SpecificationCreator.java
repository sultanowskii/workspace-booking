package org.wb.components.common;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

public abstract class SpecificationCreator<T extends Entity> {
    public abstract boolean isFieldValid(String fieldName);

    private Path<Object> fieldFromAlias(Root<T> root, String searchFieldName) {
        var subfieldNames = searchFieldName.split("\\.");

        var subfield = root.<Object>get(subfieldNames[0]);
        for (int i = 1; i < subfieldNames.length; i++) {
            subfield = subfield.<Object>get(subfieldNames[i]);
        }
        return subfield;
    }

    private Specification<T> createFilter(String searchFieldName, String searchString) throws RuntimeException {
        if (!isFieldValid(searchFieldName)) {
            throw new RuntimeException("Field '" + searchFieldName + "' is not a valid searchable field");
        }

        return (root, query, builder) -> {
            return builder.like(builder.lower(fieldFromAlias(root, searchFieldName).as(String.class)),
                    "%" + searchString.toLowerCase() + "%");
        };
    }

    public Specification<T> withFieldContaining(String searchFieldName, String searchString) throws RuntimeException {
        if (searchFieldName == null && searchString == null) {
            return null;
        }

        if (searchFieldName != null && searchString != null) {
            return createFilter(searchFieldName, searchString);
        }

        throw new RuntimeException(
                "Params 'searchFieldName' and 'searchString' work in pair - you must use either both or none");
    }
}
