package com.turfoff.turfbooking.domain.mongo.entities;

import com.turfoff.turfbooking.utilities.SlotStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

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

    private LocalDateTime dateTime;

    private int slotDuration;

    private SlotStatus slotStatus;

    private String bookingEntityId;
}