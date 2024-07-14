package br.com.bluesburger.invoice.infra.database;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.bluesburger.invoice.domain.entity.InvoiceStatus;
import br.com.bluesburger.invoice.infra.database.entity.InvoiceEntity;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

	List<InvoiceEntity> findAllByStatusOrderByCreatedTime(InvoiceStatus step);
	
	Optional<InvoiceEntity> findFirstByOrderIdAndStatusOrderByCreatedTimeDesc(String orderId, InvoiceStatus status);
	
	@Modifying
	@Transactional
	@Query(value = "delete from InvoiceEntity o where o.ID = ?1")
	void deleteById(Long id);
}
