package com.springboot.mvc.entity;


	import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.Table;
	import lombok.AllArgsConstructor;
	import lombok.Data;
	import lombok.NoArgsConstructor;
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Entity
	@Table(name="Products")
	public class ProductsEntity {
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private long id;
		@Column(name="productName")
	private String name;
	private String brand;
	private String madeIn;
	private double price;
	private int quantity;
	private double discountRate;
	private double taxPrice;
	private double discountPrice;
	private double offerPrice;
	private double finalPrice;
	private double stockValue;
}

