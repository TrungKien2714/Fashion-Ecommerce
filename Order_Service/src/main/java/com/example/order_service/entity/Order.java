package com.example.order_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Size(max = 36)
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Size(max = 36)
    @NotNull
    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @NotNull
    @Column(name = "total_price", nullable = false, precision = 15, scale = 2)
    private Integer totalPrice;

    @Size(max = 50)
    @ColumnDefault("'PENDING'")
    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "shipping_address", length = Integer.MAX_VALUE)
    private String shippingAddress;

    @Column(name = "note", length = Integer.MAX_VALUE)
    private String note;

    @ColumnDefault("0")
    @Column(name = "is_deleted")
    private Short isDeleted;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_date")
    private Instant createdDate;

    @Size(max = 255)
    @Column(name = "created_by")
    private String createdBy;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Size(max = 255)
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (this.id == null || this.id.isBlank()) {
            this.id = UUID.randomUUID().toString();
        }
        Instant now = Instant.now();
        this.createdDate = now;
        this.lastModifiedDate = now;
        if (this.status == null) {
            this.status = "PENDING";
        }
        if (this.isDeleted == null) {
            this.isDeleted = 0;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.lastModifiedDate = Instant.now();
    }
}