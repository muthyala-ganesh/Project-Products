package com.springboot.mvc.service;

	import java.util.List;
	import java.util.Optional;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

	import com.springboot.mvc.entity.ProductsEntity;
	import com.springboot.mvc.model.ProductsModel;
    import com.springboot.mvc.repository.ProductsRepository;

	@Service
	public class ProductsService {

	    @Autowired
	    private ProductsRepository productRepositoryObj; // Repository object for database operations

	    // Save product details and calculate prices based on provided model
	    public void saveProductDetails(ProductsModel productModel) {
	        // Calculate tax, discount, and final prices
	        double taxPrice = calculateTaxPrice(productModel.getPrice());
	        double discountPrice = calculateDiscountPrice(productModel.getPrice(), productModel.getDiscountRate());
	        double offerPrice = productModel.getPrice() - discountPrice;
	        double finalPrice = taxPrice + offerPrice;
	        double stockValue = productModel.getPrice() * productModel.getQuantity();

	        // Create and populate the ProductEntity/setting data to entity class from productModel to store in database.
	        ProductsEntity productEntity = new ProductsEntity();
	        productEntity.setName(productModel.getName());
	        productEntity.setBrand(productModel.getBrand());
	        productEntity.setMadeIn(productModel.getMadeIn());
	        productEntity.setPrice(productModel.getPrice());
	        productEntity.setQuantity(productModel.getQuantity());
	        productEntity.setDiscountRate(productModel.getDiscountRate());
	        productEntity.setTaxPrice(taxPrice);
	        productEntity.setDiscountPrice(discountPrice);
	        productEntity.setOfferPrice(offerPrice);
	        productEntity.setFinalPrice(finalPrice);
	        productEntity.setStockValue(stockValue);

	        // Save the entity in the database
	        productRepositoryObj.save(productEntity);
	    }

	    // Calculate tax price (18% of the product price)
	    private double calculateTaxPrice(double price) {
	        return 18 * price / 100;
	    }

	    // Calculate discount price based on the discount rate
	    private double calculateDiscountPrice(double price, double discountRate) {
	        return discountRate * price / 100;
	    }

	    // Get all products from the database
	    public List<ProductsEntity> getAllProducts() {
	        return productRepositoryObj.findAll();	    }

	    // Search for a product by its ID
	    public ProductsEntity searchById(Long id) {
	        Optional<ProductsEntity> optionalData = productRepositoryObj.findById(id);
	        if(optionalData.isPresent()) {
	        	ProductsEntity entityObj=optionalData.get();
	        return entityObj;// Return product if found, otherwise null
	        }
	        else 
	        {
	        	return null;
	        }
	    }
	    
	    // Delete a product by its ID
	    public void deleteMethod(Long id) {
	        productRepositoryObj.deleteById(id);
	    }
	    
	    //getting Edit by id.
	    public ProductsModel editById(Long id) {
			Optional<ProductsEntity> optionaldata=productRepositoryObj.findById(id);
			if(optionaldata.isPresent()) {
				ProductsModel productModel = new ProductsModel();
				ProductsEntity enityObj=optionaldata.get();
				productModel.setName(enityObj.getName());
				productModel.setBrand(enityObj.getBrand());
				productModel.setMadeIn(enityObj.getMadeIn());
				productModel.setPrice(enityObj.getPrice());
				productModel.setQuantity(enityObj.getQuantity());
				productModel.setDiscountRate(enityObj.getDiscountRate());
			return productModel;
			}
			else
			{
				return null;
			}
		}
	    
	//update 
		public void updateById(Long id,ProductsModel productModel) {
			Optional<ProductsEntity> optionaldata=productRepositoryObj.findById(id);
			if(optionaldata.isPresent()) {
				ProductsEntity entity=optionaldata.get();
				
       // Calculate tax, discount, and final prices
		        double taxPrice = calculateTaxPrice(productModel.getPrice());
		        double discountPrice = calculateDiscountPrice(productModel.getPrice(), productModel.getDiscountRate());
		        double offerPrice = productModel.getPrice() - discountPrice;
		        double finalPrice = taxPrice + offerPrice;
		        double stockValue = productModel.getPrice() * productModel.getQuantity();

       // Create and populate the ProductEntity/setting data to entity class from productModel to store in database.
		        
		        entity.setName(productModel.getName());
		        entity.setBrand(productModel.getBrand());
		        entity.setMadeIn(productModel.getMadeIn());
		        entity.setPrice(productModel.getPrice());
		        entity.setQuantity(productModel.getQuantity());
		        entity.setDiscountRate(productModel.getDiscountRate());
		        entity.setTaxPrice(taxPrice);
		        entity.setDiscountPrice(discountPrice);
		        entity.setOfferPrice(offerPrice);
		        entity.setFinalPrice(finalPrice);
		        entity.setStockValue(stockValue);

		        // Save the entity in the database
		        productRepositoryObj.save(entity);
		    }
		}
		
		
	}

