package com.tyss.rb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.rb.dto.ProfileDto;
import com.tyss.rb.response.AppResponse;
import com.tyss.rb.service.ResumeService;

@RestController
public class UserController {

	@Autowired
	private AppResponse response;

	@Autowired
	private ResumeService resumeService;

	@PostMapping(path = "/addDetails")
	public ResponseEntity<AppResponse> addAllDetails(@RequestBody ProfileDto profileDto) {

		Object data = resumeService.addAllDetails(profileDto);

		if (data != null) {
			response.setError(false);
			response.setMessage("Data Saved");
			response.setStatus(HttpStatus.ACCEPTED.value());
			response.setData(null);
			return new ResponseEntity<AppResponse>(response, HttpStatus.ACCEPTED);

		} else {
			response.setError(false);
			response.setMessage("Data not Saved");
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			response.setData(null);
			return new ResponseEntity<AppResponse>(response, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping(path = "/getAllDetails/{id}")
	public ResponseEntity<AppResponse> getAllDetails(@PathVariable Integer id) {
		ProfileDto profileDto = resumeService.getAllDetails(id);

		if (profileDto != null) {
			response.setError(false);
			response.setMessage("Resume Details");
			response.setStatus(HttpStatus.FOUND.value());
			response.setData(profileDto);
			return new ResponseEntity<AppResponse>(response, HttpStatus.FOUND);
		} else {
			response.setError(true);
			response.setMessage("Details are not present");
			response.setStatus(HttpStatus.NOT_FOUND.value());
			response.setData(null);
			return new ResponseEntity<AppResponse>(response, HttpStatus.NOT_FOUND);
		}

	}
}
