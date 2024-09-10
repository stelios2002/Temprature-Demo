package com.example.AthensTemps.temperature;

import com.example.AthensTemps.temperature.Temperature;
import com.example.AthensTemps.temperature.TemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemperatureService {

    private final TemperatureRepository temperatureRepository;

    @Autowired
    public TemperatureService(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    public Temperature saveTemperature(Temperature temperature) {
        return temperatureRepository.save(temperature);
    }

    public Optional<Temperature> getTemperatureById(Long id) {
        return temperatureRepository.findById(id);
    }

    public void deleteTemperature(Long id) {
        temperatureRepository.deleteById(id);
    }
}
