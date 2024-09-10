package com.example.AthensTemps.temperature;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Temperature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "temperature")
    private double temperature;

    public Temperature() {
    }

    public Temperature(LocalDateTime timestamp, double temperature) {
        this.timestamp = timestamp;
        this.temperature = temperature;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
