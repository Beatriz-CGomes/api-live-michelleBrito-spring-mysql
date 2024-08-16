package com.apiliveagenda.apiliveagenda.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiliveagenda.apiliveagenda.model.LiveModel;
import com.apiliveagenda.apiliveagenda.service.LiveService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/lives")
public class LiveController {

	@Autowired
	private LiveService liveService;

	@GetMapping
	public ResponseEntity<Page<LiveModel>> getAll(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
			@RequestParam(required = false) String flag) {
		Page<LiveModel> livePage = liveService.findAll(pageable, flag);
		if (livePage.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Page<LiveModel>>(livePage, HttpStatus.OK);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<LiveModel> getById(@PathVariable Long id) {
		Optional<LiveModel> liveOptional = liveService.findById(id);
		if (!liveOptional.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<LiveModel>(liveOptional.get(), HttpStatus.OK);
		}
	}

	@PostMapping
	public ResponseEntity<LiveModel> post(@RequestBody LiveModel live) {
		live.setRegistrationDate(LocalDateTime.now());
		return new ResponseEntity<LiveModel>(liveService.post(live), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<LiveModel> liveOptional = liveService.findById(id);
		if (!liveOptional.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			liveService.delete(liveOptional.get());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<LiveModel> update(@PathVariable Long id, @RequestBody LiveModel live) {
		Optional<LiveModel> liveOptional = liveService.findById(id);
		if (!liveOptional.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			live.setId(liveOptional.get().getId());
			return new ResponseEntity<LiveModel>(liveService.post(live), HttpStatus.OK);
		}
	}
}
