package com.ortoroverbasso.ortorovebasso.dto.orders;

import java.time.LocalDateTime;
import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.ProductQuantityDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.CarrierRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.shipping_address.ShippingAddressRequestDto;

public class OrderRequestDto {
    private String internalReference;
    private LocalDateTime dateAdd;
    private Double totalPaidTaxIncl;
    private Double totalPaidTaxExcl;
    private Double totalShippingTaxIncl;
    private Double totalShippingTaxExcl;
    private String status;

    private ShippingAddressRequestDto shippingAddress;
    private List<ProductQuantityDto> products; // ✅ tipo corretto
    private List<CarrierRequestDto> carriers;

    // Getters & Setters

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

    public Double getTotalShippingTaxIncl() {
        return totalShippingTaxIncl;
    }

    public void setTotalShippingTaxIncl(Double totalShippingTaxIncl) {
        this.totalShippingTaxIncl = totalShippingTaxIncl;
    }

    public Double getTotalShippingTaxExcl() {
        return totalShippingTaxExcl;
    }

    public void setTotalShippingTaxExcl(Double totalShippingTaxExcl) {
        this.totalShippingTaxExcl = totalShippingTaxExcl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ShippingAddressRequestDto getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddressRequestDto shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<ProductQuantityDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductQuantityDto> products) {
        this.products = products;
    }

    public List<CarrierRequestDto> getCarriers() {
        return carriers;
    }

    public void setCarriers(List<CarrierRequestDto> carriers) {
        this.carriers = carriers;
    }
}
