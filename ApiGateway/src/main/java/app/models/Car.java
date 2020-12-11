package app.models;

import lombok.Data;
import lombok.NonNull;

@Data
public class Car {
    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private String brand;
    @NonNull
    private int year;
    @NonNull
    private long km;

    public Car() {
    }

    public Car(Long id, @NonNull String brand, @NonNull int year, @NonNull long km) {
        this.id = id;
        this.brand = brand;
        this.year = year;
        this.km = km;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getKm() {
        return km;
    }

    public void setKm(long km) {
        this.km = km;
    }
}
