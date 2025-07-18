package com.ortoroverbasso.ortorovebasso.dto.filters.product_filters;

public class PriceRangeResponseDto {
    private double from;
    private double to;
    private long count;

    public PriceRangeResponseDto() {
        // Costruttore vuoto per Jackson
    }

    public PriceRangeResponseDto(double from, double to, long count) {
        this.from = from;
        this.to = to;
        this.count = count;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

}
