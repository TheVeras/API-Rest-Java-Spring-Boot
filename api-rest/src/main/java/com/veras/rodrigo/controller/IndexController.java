package com.veras.rodrigo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
	
	@GetMapping(path = "/api/index")
	public String check(){
		return "Index";
	}
}
