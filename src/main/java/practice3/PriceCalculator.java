package practice3;

import java.math.BigDecimal;
import java.util.List;

public class PriceCalculator {

    public static BigDecimal calculate(List<OrderLineItem> orderLineItemList, List<BigDecimal> discounts) {
        Order order = new Order(orderLineItemList, discounts);

        BigDecimal subTotal = new BigDecimal(0);

        subTotal = order.getOrderLineItemList().stream().map(OrderLineItem::getPrice).reduce(subTotal, BigDecimal::add);

        subTotal = order.getDiscounts().stream().reduce(subTotal, BigDecimal::subtract);

        BigDecimal tax = subTotal.multiply(order.getTax());

        return subTotal.add(tax);
    }
}