package org.wb.components.common;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

public abstract class EntitySpecificationBuilder<T extends Entity> {
    protected Specification<T> spec;

    public abstract boolean isFieldValid(String fieldName);

    protected void andOtherSpec(Specification<T> other) {
        if (this.spec == null) {
            this.spec = other;
        } else {
            this.spec = this.spec.and(other);
        }
    }

    protected Path<Object> getNested(Root<T> root, String searchFieldName) {
        var subfieldNames = searchFieldName.split("\\.");

        var subfield = root.<Object>get(subfieldNames[0]);
        for (int i = 1; i < subfieldNames.length; i++) {
            subfield = subfield.<Object>get(subfieldNames[i]);
        }
        return subfield;
    }

    private void createFilter(String searchFieldName, String searchString) throws RuntimeException {
        if (!isFieldValid(searchFieldName)) {
            throw new RuntimeException("Field '" + searchFieldName + "' is not a valid searchable field");
        }

        andOtherSpec(
                (root, query, builder) -> {
                    return builder.like(builder.lower(getNested(root, searchFieldName).as(String.class)),
                            "%" + searchString.toLowerCase() + "%");
                });
    }

    public EntitySpecificationBuilder<T> withFieldContaining(String searchFieldName, String searchString)
            throws RuntimeException {
        if (searchFieldName == null && searchString == null) {
            return this;
        }

        if (searchFieldName != null && searchString != null) {
            createFilter(searchFieldName, searchString);
            return this;
        }

        throw new RuntimeException(
                "Params 'searchFieldName' and 'searchString' work in pair - you must use either both or none");
    }

    public Specification<T> build() {
        return this.spec;
    }
}
