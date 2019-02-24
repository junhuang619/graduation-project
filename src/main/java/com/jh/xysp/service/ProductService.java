package com.jh.xysp.service;

import java.io.InputStream;
import java.util.List;

import com.jh.xysp.dto.ImageHolder;
import com.jh.xysp.dto.ProductExecution;
import com.jh.xysp.entity.Product;
import com.jh.xysp.exception.ProductOperationException;

public interface ProductService {
	ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImageList)
            throws ProductOperationException;
	Product queryProductById(long productId);
	ProductExecution modifyProduct(Product product, ImageHolder imageHolder, List<ImageHolder> prodImgDetailList) throws ProductOperationException;
	ProductExecution queryProductionList(Product productCondition, int pageIndex, int pageSize) throws ProductOperationException;
}
