package com.autonoma.service;


import com.autonoma.dto.request.ProductoRequest;
import com.autonoma.dto.request.UpdateProductoRequest;
import com.autonoma.dto.response.MessageResponse;
import com.autonoma.dto.response.ProductoResponse;

import java.util.List;

public interface ProductoService {

    MessageResponse save(ProductoRequest request);
    MessageResponse update(Integer id, UpdateProductoRequest request);
    List<ProductoResponse> findAll();
    ProductoResponse findById(Integer id);
    void delete(Integer id);

}
