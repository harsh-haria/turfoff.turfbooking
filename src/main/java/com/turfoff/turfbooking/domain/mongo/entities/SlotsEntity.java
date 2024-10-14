package com.turfoff.turfbooking.domain.mongo.entities;

import com.turfoff.turfbooking.utilities.SlotStatus;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Document(collection = "Slots")
public class SlotsEntity {
    @Id
    private String id;

    private String turfId;

    private LocalDate date;

    @Embedded
    private TimeSlot slot;

    private SlotStatus slotStatus;

    private String bookingEntityId;
}