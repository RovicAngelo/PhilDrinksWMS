package com.lanuza.phildrinkswms.data.service;

import com.lanuza.phildrinkswms.data.entity.Product;
import com.lanuza.phildrinkswms.data.entity.Supplier;
import com.lanuza.phildrinkswms.data.repository.ProductRepository;
import com.lanuza.phildrinkswms.data.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@AllArgsConstructor
@Service
public class phildrinksService{
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    //*****************ProductService***********************//
    public List<Product> findAllProducts(String stringFilter){
        if(stringFilter == null || stringFilter.isEmpty()){
            return productRepository.findAll();
        }else{
            return productRepository.search(stringFilter);
        }
    }
    public long countProducts(){
        return productRepository.count();
    }


    public void saveProduct(Product product){
        if(product == null){
            System.err.println("Product is empty!!!");
            return;
        }
            productRepository.save(product);
    }
    public void deleteProduct(Product product){
        productRepository.delete(product);
    }


    //*****************SupplierService***********************//
    public List<Supplier> findAllSuppliers(String stringFilter){
        if(stringFilter == null || stringFilter.isEmpty()){
            return supplierRepository.findAll();
        }else{
            return supplierRepository.search(stringFilter);
        }
    }
    public long countSuppliers(){
        return supplierRepository.count();
    }

    public void saveSupplier(Supplier supplier){
        if(supplier == null){
            System.err.println("Supplier is empty!!!");
            return;
        }
        supplierRepository.save(supplier);
    }
    public void deleteSupplier(Supplier supplier){
        supplierRepository.delete(supplier);
    }

    }