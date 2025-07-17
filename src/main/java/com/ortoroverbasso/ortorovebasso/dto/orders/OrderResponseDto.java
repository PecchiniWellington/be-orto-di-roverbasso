package com.ortoroverbasso.ortorovebasso.dto.orders;

import java.time.LocalDateTime;
import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.ProductResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.CarrierResponseDto;
import com.ortoroverbasso.ortorovebasso.dto.user.shipping_address.ShippingAddressResponseDto;

public class OrderResponseDto {

    private Long id;
    private String internalReference;
    private LocalDateTime dateAdd;
    private Double totalPaidTaxIncl;
    private Double totalPaidTaxExcl;
    private Double totalShippingTaxExcl;
    private Double totalShippingTaxIncl;
    private String status;
    private ShippingAddressResponseDto shippingAddress;
    private List<ProductResponseDto> products;
    private List<CarrierResponseDto> carriers;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInternalReference() {
        return internalReference;
    }

    public void setInternalReference(String internalReference) {
        this.internalReference = internalReference;
    }

    public LocalDateTime getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(LocalDateTime dateAdd) {
        this.dateAdd = dateAdd;
    }

    public Double getTotalPaidTaxIncl() {
        return totalPaidTaxIncl;
    }

    public void setTotalPaidTaxIncl(Double totalPaidTaxIncl) {
        this.totalPaidTaxIncl = totalPaidTaxIncl;
    }

    public Double getTotalPaidTaxExcl() {
        return totalPaidTaxExcl;
    }

    public void setTotalPaidTaxExcl(Double totalPaidTaxExcl) {
        this.totalPaidTaxExcl = totalPaidTaxExcl;
    }

    public Double getTotalShippingTaxExcl() {
        return totalShippingTaxExcl;
    }

    public void setTotalShippingTaxExcl(Double totalShippingTaxExcl) {
        this.totalShippingTaxExcl = totalShippingTaxExcl;
    }

    public Double getTotalShippingTaxIncl() {
        return totalShippingTaxIncl;
    }

    public void setTotalShippingTaxIncl(Double totalShippingTaxIncl) {
        this.totalShippingTaxIncl = totalShippingTaxIncl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ShippingAddressResponseDto getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddressResponseDto shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<ProductResponseDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponseDto> products) {
        this.products = products;
    }

    public List<CarrierResponseDto> getCarriers() {
        return carriers;
    }

    public void setCarriers(List<CarrierResponseDto> carriers) {
        this.carriers = carriers;
    }
}
