package com.example.mobilehomesbooking.service;

import com.example.mobilehomesbooking.model.Parcel;
import com.example.mobilehomesbooking.model.User;
import com.example.mobilehomesbooking.repository.ParcelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParcelService {

    @Autowired
    private ParcelRepository parcelRepository;

    public Parcel addParcel(Parcel parcel, User owner) {
        parcel.setOwner(owner);
        return parcelRepository.save(parcel);
    }

    public List<Parcel> getAllParcels() {
        return parcelRepository.findAll();
    }

    public List<Parcel> getParcelsByOwner(Long ownerId) {
        return parcelRepository.findByOwnerId(ownerId);
    }

    public Parcel getParcelById(Long id) {
        return parcelRepository.findById(id).orElseThrow(() -> new RuntimeException("Parcel not found"));
    }


    public Parcel updateParcel(Long id, Parcel updatedParcel) {
            Parcel parcel = parcelRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Parcel not found"));

            parcel.setTitle(updatedParcel.getTitle());
            parcel.setDescription(updatedParcel.getDescription());
            parcel.setLocation(updatedParcel.getLocation());
            parcel.setSizeSquareMeters(updatedParcel.getSizeSquareMeters());
            parcel.setPricePerYear(updatedParcel.getPricePerYear());
            parcel.setHasElectricity(updatedParcel.isHasElectricity());
            parcel.setHasWater(updatedParcel.isHasWater());
            parcel.setHasWifi(updatedParcel.isHasWifi());

            return parcelRepository.save(parcel);
        }




}
