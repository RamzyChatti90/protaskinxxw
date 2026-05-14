package com.example.protaskinxxw.service.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for DashboardStats.
 */
public class DashboardStatsDTO implements Serializable {

    private Long id;
    private Integer totalItems;
    private Integer completedCount;
    private Float completionRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Integer getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(Integer completedCount) {
        this.completedCount = completedCount;
    }

    public Float getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Float completionRate) {
        this.completionRate = completionRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DashboardStatsDTO)) return false;
        return Objects.equals(id, ((DashboardStatsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DashboardStatsDTO{" +
            "id=" + getId() +
            "}";
    }
}