package com.example.cars.dto;

public class ModelDTO {
    private Long id;
    private Long manufacturerId;
    private String name;
    private String category;
    private String priceRange;
    private int releaseYear;
    private boolean isActive;

    public ModelDTO() {
    }

    public ModelDTO(Long id, Long manufacturerId, String name, String category, String priceRange, int releaseYear, boolean isActive) {
        this.id = id;
        this.manufacturerId = manufacturerId;
        this.name = name;
        this.category = category;
        this.priceRange = priceRange;
        this.releaseYear = releaseYear;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
