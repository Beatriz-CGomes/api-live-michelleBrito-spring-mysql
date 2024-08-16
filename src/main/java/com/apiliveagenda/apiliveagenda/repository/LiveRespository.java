package com.apiliveagenda.apiliveagenda.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apiliveagenda.apiliveagenda.model.LiveModel;

@Repository
public interface LiveRespository extends JpaRepository<LiveModel, Long> {
	
	Page<LiveModel> findByLiveDateAfterOrderByLiveDateAsc(LocalDateTime date, Pageable pageable);
	Page<LiveModel> findByLiveDateAfterOrderByLiveDateDesc(LocalDateTime date, Pageable pageable);
}
