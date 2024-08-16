package com.apiliveagenda.apiliveagenda.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.apiliveagenda.apiliveagenda.model.LiveModel;
import com.apiliveagenda.apiliveagenda.repository.LiveRespository;

@Service
public class LiveService {

	@Autowired
	private LiveRespository liveRespository;

	public Page<LiveModel> findAll(Pageable pageable, String flag) {
		if (flag != null && flag.equals("next")) {
			return liveRespository.findByLiveDateAfterOrderByLiveDateAsc(LocalDateTime.now(), pageable);
		} else if (flag != null && flag.equals("previous")) {
			return liveRespository.findByLiveDateAfterOrderByLiveDateDesc(LocalDateTime.now(), pageable);
		} else {
			return liveRespository.findAll(pageable);
		}
	}

	public Optional<LiveModel> findById(Long id) {
		return liveRespository.findById(id);
	}

	public LiveModel post(LiveModel live) {
		return liveRespository.save(live);
	}

	public void delete(LiveModel live) {
		liveRespository.delete(live);
	}
}
