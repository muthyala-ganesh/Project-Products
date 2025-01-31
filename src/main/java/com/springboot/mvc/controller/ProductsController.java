package com.springboot.mvc.controller;

	import java.util.HashMap;
	import java.util.List;


	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.validation.BindingResult;
	import org.springframework.validation.FieldError;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.mvc.entity.ProductsEntity;
import com.springboot.mvc.model.ProductsModel;
import com.springboot.mvc.service.ProductsService;

import jakarta.validation.Valid;


	@Controller
	public class ProductsController {

	    @Autowired
	    ProductsService productServiceObj; // Injecting ProductService into controller.

	     // To display the product form,get product form with default values
	    @GetMapping("/productform")
	    public String getProductForm(Model model) {
	     //to set default values.
	    	ProductsModel productModel=new ProductsModel();
	  		productModel.setMadeIn("India");
	  		productModel.setQuantity(1);
	  		productModel.setDiscountRate(20.);
	  		model.addAttribute("productModel",productModel);
	  		
	        return "add-products"; // Return the add-products HTML page
	    }
	    
	    //save product,validation errors
	    @PostMapping("/submitproduct")
	    public String saveProduct(@Valid ProductsModel productModel, BindingResult bindingResultObj, Model model) {
	        System.out.println(productModel); // For debugging, print product data 
	   // Check if there are validation errors
	        if (bindingResultObj.hasErrors()) {
	            // Create a HashMap to store field names and their corresponding error messages
	            HashMap<String, String> validationErrorsObj = new HashMap<>();

	           // Loop through the errors and populate the HashMap
	            for (FieldError fieldErrorObj : bindingResultObj.getFieldErrors()) {
	                validationErrorsObj.put(fieldErrorObj.getField(), fieldErrorObj.getDefaultMessage());
	            }

	          // Add the product model and validation errors to the model
	            model.addAttribute("productModel", productModel);  // Add the product data to repopulate the form
	            model.addAttribute("validationErrorsObj", validationErrorsObj);  // Add validation errors to display in the view

	         // Return the same form view with errors
	            return "add-products";
	        }

	        // Save product details if validation passes
	        productServiceObj.saveProductDetails(productModel); 

	       // Redirect to the product details page after successful save
	        return "redirect:/productdetails"; 
	    }


	    // To display all products in a list
	    @GetMapping("/productdetails")
	    public String AllProducts(Model model) {
	        List<ProductsEntity> productObj = productServiceObj.getAllProducts(); // Get all products
	        model.addAttribute("products", productObj); // Add products to the model for view
	        return "products-List"; // Return the view that displays the products list
	    }

	    // To display the search form for searching products by ID
	    @GetMapping("/searchform")
	    public String getSearchForm() {
	        return "search-product"; // Return the search product page
	    }

	    // To search for a product by its ID
	    @PostMapping("/searchbyid")
	    public String searchById(@RequestParam Long id, Model model) {
	        ProductsEntity productList = productServiceObj.searchById(id); // Find product by ID
	        if (productList != null) {
	            model.addAttribute("product", productList); // Add found product to model
	            return "search-product-result"; // Return a result page that displays the found product
	        } else {
	            model.addAttribute("message", "Product not found for ID: " + id); // Handle product not found
	            return "search-product"; // Return to the search page with a message
	        }
	    }

	    // To delete a product by its ID
	    @GetMapping("/delete/{id}")
	    public String deleteProduct(@PathVariable Long id) {
	        productServiceObj.deleteMethod(id); // Call delete method in service layer
	        return "redirect:/productdetails"; // Redirect to the product list page after deletion
	    }
	    
	   // To edit product by its id
	 
	    @GetMapping("/edit/{id}")
	    public String editProductById(@PathVariable Long id,  Model model) {
	      
	        ProductsModel product = productServiceObj.editById(id);
	        
	        model.addAttribute("product", product); 
	        model.addAttribute("id", id);
	        
	        return "edit-product";
	    }
	    
	    //Update and also add validations
	    @PostMapping("/edit/{id}")
	    public String updateProduct(@PathVariable Long id, @Valid ProductsModel productModel, 
	                                BindingResult bindingResultObj, Model model) {
	        if (bindingResultObj.hasErrors()) {
	        	HashMap<String, String> validationErrorsObj = new HashMap<>();
	        	 for (FieldError fieldErrorObj : bindingResultObj.getFieldErrors()) {
		                validationErrorsObj.put(fieldErrorObj.getField(), fieldErrorObj.getDefaultMessage());
		            }
	        	model.addAttribute("product",productModel);
	            model.addAttribute("validationErrorsObj",validationErrorsObj);
	            return "edit-product"; // Return to the edit form if validation fails
	        }
	        
	        // Proceed with updating the product
	        productServiceObj.updateById(id, productModel);
	        
	        return "redirect:/productdetails"; // Redirect to the product details page after successful update
	    }

	}
