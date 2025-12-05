package com.autonoma.service;


import com.autonoma.dto.request.ProductoRequest;
import com.autonoma.dto.request.UpdateProductoRequest;
import com.autonoma.dto.response.MessageResponse;
import com.autonoma.dto.response.ProductoResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ProductoService {

    MessageResponse save(ProductoRequest request, HttpServletRequest httpRequest);
    MessageResponse update(Integer id, UpdateProductoRequest reques, HttpServletRequest httpRequestt);
    List<ProductoResponse> findAll();
    ProductoResponse findById(Integer id);
    void delete(Integer id);

}
