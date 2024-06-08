package com.turfoff.turfbooking.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class EntityBase {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

    private Long createdBy;

    private Long lastModifiedBy;

}
