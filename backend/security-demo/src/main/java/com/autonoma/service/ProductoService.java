package com.autonoma.service;


import com.autonoma.dto.request.ProductoRequest;
import com.autonoma.dto.response.ProductoResponse;

import java.util.List;

public interface ProductoService {

    ProductoResponse save(ProductoRequest request);
    ProductoResponse update(Integer id, ProductoRequest request);
    List<ProductoResponse> findAll();
    ProductoResponse findById(Integer id);
    void delete(Integer id);

}
