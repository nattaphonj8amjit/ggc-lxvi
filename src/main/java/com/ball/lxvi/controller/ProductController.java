package com.ball.lxvi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ball.lxvi.service.ProductService;
import com.ball.lxvi.service.TranslateService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;
	@Autowired
	TranslateService translate;

	@GetMapping("/save")
	public long save(@RequestParam(value = "name") String name, @RequestParam(value = "description") String description,
			@RequestParam(value = "price") double price) {
		return productService.save(name, price, description);
	}

	@GetMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable(value = "id") Long id) {
		return productService.get(id);
	}

	@GetMapping("/delete/{id}")
	public boolean delete(@PathVariable(value = "id") Long id) {
		return productService.delete(id);
	}

	@GetMapping("/update/{id}")
	public boolean update(@PathVariable(value = "id") Long id) {
		return productService.update(id);
	}

	@GetMapping("/query")
	public List<Map<String, Object>> query() {
		return productService.query();
	}

	@GetMapping("/translate")
	public String translate(@RequestParam(value = "txt") String txt) throws Exception {
		return translate.translate(txt);
	}

}
