package practice2;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class Receipt {

    private BigDecimal tax;

    public Receipt() {
        tax = new BigDecimal(0.1);
        tax = tax.setScale(2, BigDecimal.ROUND_HALF_UP); //BigDecimal.setScale()方法用于格式化小数点,四舍五入
    }


    public double CalculateGrandTotal(List<Product> products, List<OrderItem> items) {
        BigDecimal subTotal = calculateSubtotal(products, items);

        subTotal = calculateSubtractDiscounts(products, items, subTotal);

        BigDecimal grandTotal = AddTax(subTotal);

        return convertTotalToDoubleType(grandTotal);
    }

    private double convertTotalToDoubleType(BigDecimal grandTotal) {
        return grandTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private BigDecimal AddTax(BigDecimal subTotal) {
        BigDecimal taxTotal = subTotal.multiply(tax);
        return subTotal.add(taxTotal);
    }

    private BigDecimal calculateSubtractDiscounts(List<Product> products, List<OrderItem> items, BigDecimal subTotal) {
        return products.stream().map(product -> {
            OrderItem curItem = findOrderItemByProduct(items, product);
            return calculateProductDiscounts(product, curItem);
        }).reduce(subTotal, BigDecimal::subtract);
    }

    private BigDecimal calculateProductDiscounts(Product product, OrderItem curItem) {
        return product.getPrice()
                .multiply(product.getDiscountRate())
                .multiply(new BigDecimal(curItem.getCount()));
    }


    private OrderItem findOrderItemByProduct(List<OrderItem> items, Product product) {
        return items.stream().filter(item -> item.getCode() == product.getCode()).findFirst().get();
    }

    private BigDecimal calculateSubtotal(List<Product> products, List<OrderItem> items) {
        BigDecimal subTotal = new BigDecimal(0);
        return products.stream().map(product -> {
            OrderItem item = findOrderItemByProduct(items, product);
            return product.getPrice().multiply(new BigDecimal(item.getCount()));
        }).reduce(subTotal, BigDecimal::add);
    }
}