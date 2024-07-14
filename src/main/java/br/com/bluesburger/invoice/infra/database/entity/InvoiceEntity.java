package br.com.bluesburger.invoice.infra.database.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.bluesburger.invoice.domain.entity.InvoiceStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(schema = "bluesburger-invoice", name = "TB_INVOICE")
@Builder
public class InvoiceEntity implements Serializable {
	
	private static final long serialVersionUID = 4781858089323528412L;

	@Id
	@Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
    private Long id;

    @CreationTimestamp
    @Column(name = "CREATED_TIME")
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "UPDATED_TIME")
    private LocalDateTime updatedTime;
    
    @Setter
    @Default
    @NotNull
    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private InvoiceStatus status = InvoiceStatus.PENDING;
    
    @Setter
    @NotNull
    @NonNull
    @Column(name = "ORDER_ID")
    private String orderId;
}
