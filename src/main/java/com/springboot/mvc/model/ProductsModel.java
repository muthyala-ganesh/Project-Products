package com.springboot.mvc.model;

	import jakarta.validation.Valid;
	import jakarta.validation.constraints.Max;
	import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
	import lombok.Data;
	import lombok.NoArgsConstructor;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Valid
	public class ProductsModel {
		@NotBlank(message="product name cannot be blank")
				private String name;
		@NotBlank(message="product brand cannot be blank")
				private String brand;
		@NotBlank(message="product madeIn cannot be blank")
				private String madeIn;
		@Positive(message="price must be greater than zero")
				private double price;
		@Min(value=1,message="quantity must be atleast 1")
				private int quantity;
		@Max(value=100,message="discount should not be more than 100")
				private double discountRate;
	}
