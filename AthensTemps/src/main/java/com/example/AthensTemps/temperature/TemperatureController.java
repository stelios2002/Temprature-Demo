package com.example.AthensTemps.temperature;

import com.example.AthensTemps.temperature.Temperature;
import com.example.AthensTemps.temperature.TemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/temperature")
public class TemperatureController {

    private final TemperatureService temperatureService;

    @Autowired
    public TemperatureController(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    // POST /temperature - Αποθήκευση θερμοκρασίας
    @PostMapping
    public ResponseEntity<Temperature> saveTemperature(@RequestBody Temperature temperature) {
        temperature.setTimestamp(LocalDateTime.now());  // Ορισμός της τρέχουσας ημερομηνίας και ώρας
        Temperature savedTemperature = temperatureService.saveTemperature(temperature);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTemperature.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedTemperature);
    }

    // GET /temperature/{id} - Επιστροφή θερμοκρασίας με βάση το ID
    @GetMapping("/{id}")
    public ResponseEntity<Temperature> getTemperatureById(@PathVariable Long id) {
        Optional<Temperature> temperature = temperatureService.getTemperatureById(id);
        return temperature.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /temperature/{id} - Διαγραφή θερμοκρασίας με βάση το ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemperature(@PathVariable Long id) {
        Optional<Temperature> temperature = temperatureService.getTemperatureById(id);
        if (temperature.isPresent()) {
            temperatureService.deleteTemperature(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
