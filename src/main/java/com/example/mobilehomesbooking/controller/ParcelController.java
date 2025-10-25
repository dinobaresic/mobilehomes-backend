package com.example.mobilehomesbooking.controller;

import com.example.mobilehomesbooking.model.Parcel;
import com.example.mobilehomesbooking.model.User;
import com.example.mobilehomesbooking.repository.ParcelRepository;
import com.example.mobilehomesbooking.repository.UserRepository;
import com.example.mobilehomesbooking.service.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/parcels")
@CrossOrigin(origins = "http://localhost:3000")
public class ParcelController {

    @Autowired
    private ParcelService parcelService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParcelRepository parcelRepository;

    @PostMapping("/add/{ownerId}")
    public Parcel addParcel(@PathVariable Long ownerId, @RequestBody Parcel parcel) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        return parcelService.addParcel(parcel, owner);
    }

    @PostMapping("/upload-image/{id}")
    public ResponseEntity<?> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        try {
            Parcel parcel = parcelRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Parcel not found"));

            // putanja do frontend/public/uploads foldera
            String uploadDir = "../frontend/public/uploads/";
            Files.createDirectories(Paths.get(uploadDir));

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // spremi relativnu putanju koja će raditi na frontendu
            parcel.setImageUrl("/uploads/" + fileName);
            parcelRepository.save(parcel);

            return ResponseEntity.ok("✅ Slika uspješno spremljena: " + parcel.getImageUrl());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("❌ Greška pri spremanju slike.");
        }
    }


    @PostMapping("/owner/{ownerId}")
    public ResponseEntity<Parcel> createParcel(
            @PathVariable Long ownerId,
            @RequestBody Parcel parcelRequest) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        // poveži parcelu s ownerom
        parcelRequest.setOwner(owner);

        // spremi parcelu
        Parcel savedParcel = parcelRepository.save(parcelRequest);

        return ResponseEntity.ok(savedParcel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParcel(@PathVariable Long id) {
        if (!parcelRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        parcelRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parcel> updateParcel(@PathVariable Long id, @RequestBody Parcel parcel) {
        return ResponseEntity.ok(parcelService.updateParcel(id, parcel));
    }

    @GetMapping
    public ResponseEntity<List<Parcel>> getAllParcels() {
        List<Parcel> parcels = parcelRepository.findAll();
        return ResponseEntity.ok(parcels);
    }



    @GetMapping("/owner/{ownerId}")
    public List<Parcel> getParcelsByOwner(@PathVariable Long ownerId) {
        return parcelService.getParcelsByOwner(ownerId);
    }

    @GetMapping("/{id}")
    public Parcel getParcelById(@PathVariable Long id) {
        return parcelService.getParcelById(id);
    }
}
