package com.example.mobilehomesbooking.repository;

import com.example.mobilehomesbooking.model.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    List<Parcel> findByOwnerId(Long ownerId);



}
