package com.ortoroverbasso.ortorovebasso.dto.orders;

import java.util.List;

import com.ortoroverbasso.ortorovebasso.dto.product.ProductRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.shipping.CarrierRequestDto;
import com.ortoroverbasso.ortorovebasso.dto.user.shipping_address.ShippingAddressRequestDto;

public class OrderRequestDto {

    private String internalReference;
    private String dateAdd;
    private Double totalPaidTaxIncl;
    private Double totalPaidTaxExcl;
    private Double totalShippingTaxExcl;
    private Double totalShippingTaxIncl;
    private String status;
    private ShippingAddressRequestDto shippingAddress;
    private List<ProductRequestDto> products;
    private List<CarrierRequestDto> carriers;

    // Getters and Setters
    public String getInternalReference() {
        return internalReference;
    }

    public void setInternalReference(String internalReference) {
        this.internalReference = internalReference;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
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

    public ShippingAddressRequestDto getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddressRequestDto shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<ProductRequestDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRequestDto> products) {
        this.products = products;
    }

    public List<CarrierRequestDto> getCarriers() {
        return carriers;
    }

    public void setCarriers(List<CarrierRequestDto> carriers) {
        this.carriers = carriers;
    }
}
