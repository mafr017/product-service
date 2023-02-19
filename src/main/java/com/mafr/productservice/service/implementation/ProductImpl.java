package com.mafr.productservice.service.implementation;

import com.mafr.productservice.entity.ProductEntity;
import com.mafr.productservice.repository.ProductRepository;
import com.mafr.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductImpl implements ProductService {
    private final ProductRepository repository;

    @Override
    public List<ProductEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public ProductEntity getByID(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public ProductEntity save(ProductEntity param) {
        return repository.save(param);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ProductEntity update(ProductEntity param) {
        Optional<ProductEntity> product = repository.findById(param.getId());
        if (product.isEmpty()) {
            throw new NotFoundException("Product not exist...");
        }
        product.get().setName(param.getName());
        if (param.getDescription() != null){
            product.get().setDescription(param.getDescription());
        }
        product.get().setStock(param.getStock());
        product.get().setPrice(param.getPrice());
        return repository.save(product.get());
    }
}
