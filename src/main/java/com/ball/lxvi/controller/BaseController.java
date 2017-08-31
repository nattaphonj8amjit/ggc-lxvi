package com.ball.lxvi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ball.lxvi.service.ProductService;

@Controller
@RequestMapping("/base")
public class BaseController {

	@Autowired
	ProductService productService;

	@RequestMapping("/test")
	public String greeting( Model model) {
//		model.addAttribute("saleOrder", productService.example());
		return "test";
	}

	@GetMapping("/example")
	@ResponseBody
	public Map<String, Object> example() {
		return productService.example();
	}

}
