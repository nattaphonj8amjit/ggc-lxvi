package com.ball.lxvi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ball.lxvi.service.ProductService;

@Controller
@RequestMapping("/base")
public class BaseController {

	@Autowired
	ProductService productService;
	
    @RequestMapping("/greeting/{id}")
    public String greeting(@PathVariable(value="id") long id, Model model) {
        model.addAttribute("product", productService.get(id));
        return "greeting";
    }
	
}
