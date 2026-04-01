package com.concesionario.vigorMotors.entity;
 
import java.math.BigDecimal;
import java.time.LocalDateTime;
 
import org.hibernate.annotations.CreationTimestamp;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
 
@Entity
@Data
@Table(name = "vehicles")
public class Vehicle {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
 
    @Column(name = "brand", nullable = false, length = 100)
    private String brand;
 
    @Column(name = "model", nullable = false, length = 100)
    private String model;
 
    @Column(name = "year", nullable = false)
    private Integer year;
 
    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;
 
    @Column(name = "stock", nullable = false)
    private Integer stock = 0;
 
    @Column(name = "color", length = 50)
    private String color;
 
    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private FuelType fuelType;
 
    @Enumerated(EnumType.STRING)
    @Column(name = "transmission")
    private Transmission transmission;
 
    @Column(name = "mileage")
    private Integer mileage = 0;
 
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
 
    @Column(name = "image_url", length = 500)
    private String imageUrl;
 
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
 
    public enum FuelType {
        Gasolina, Diesel, Híbrido, Eléctrico
    }
 
    public enum Transmission {
        Manual, Automática
    }
}