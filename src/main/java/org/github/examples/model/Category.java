package org.github.examples.model;

import org.springframework.data.domain.Persistable;

public class Category implements Persistable<Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * mandatory default ctor
     */
    public Category() {
    }

    public Category(int id) {
        this.id = id;
    }

    private String name;
    private Integer id;
    private int departmentId;

    private String description;

    public Integer getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return id != null ? id.equals(category.id) : category.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
