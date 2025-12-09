package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/*
	Things to test:

	- Standrd shipping cost test
	- Express shipping cost test
	- Status esting for delivery and shipping

*/


public class OrderTest {

    @Test
    void testTotalSingleItemStandardShipping() {
        Bouquet b1 = new Bouquet(
                "Test Bouquet",
                "Simple test bouquet",
                "TestCategory",
                10.0,
                true
        );

        List<Bouquet> items = Arrays.asList(b1);
        Order order = new Order(items, ShippingMethod.STANDARD);
        double expectedTotal = 10.0 + ShippingMethod.STANDARD.getCost();
        assertEquals(expectedTotal, order.getTotal(), 0.0001,
                "Total should be item price plus STANDARD shipping cost");
    }

    @Test
    void testTotalMultipleItemsExpressShipping() {
        Bouquet b1 = new Bouquet(
                "Bouquet A",
                "First test bouquet",
                "TestCategory",
                15.0,
                true
        );
        Bouquet b2 = new Bouquet(
                "Bouquet B",
                "Second test bouquet",
                "TestCategory",
                20.0,
                true
        );

        List<Bouquet> items = Arrays.asList(b1, b2);
        Order order = new Order(items, ShippingMethod.EXPRESS);
        double expectedTotal = 15.0 + 20.0 + ShippingMethod.EXPRESS.getCost();
        assertEquals(expectedTotal, order.getTotal(), 0.0001,
                "Total should be sum of item prices plus EXPRESS shipping cost");
    }

    @Test
    void testInitialStatusAndDeliveryStatus() {
        Bouquet b1 = new Bouquet(
                "Status Test",
                "Status test bouquet",
                "TestCategory",
                12.0,
                true
        );

        Order order = new Order(Arrays.asList(b1), ShippingMethod.STANDARD);
        assertEquals(OrderStatus.PENDING, order.getStatus(),
                "New orders should start with PENDING status");
        assertEquals(DeliveryStatus.NOT_SHIPPED, order.getDeliveryStatus(),
                "New orders should start with NOT_SHIPPED delivery status");
    }
}