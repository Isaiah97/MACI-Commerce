package model;

import static org.junit.jupiter.api.Assertion.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


/* List of tests;

- Standard shipping test
- Express shipping test
- Status on Delivery and shipping test

*/

public class OrderTest {

	@Test
	void testTotalSingleItemStandardShipping() {
		Bouquet b1 = new Bouquet(
		"Test Bouquet",
		"Simple test bouquest",
		"Test Category",
		10.0,
		true);

		List<Bouquet> items = Arrays.asList(b1);
		Order order = new Order(items, ShippingMethod.STANDARD);
		double expectTotal = 10.0 + ShippingMethod.STANDARD.getCost();
		assertEquals(expectTotal, order.getTotal(), 0.0001, 
			"Total should be listing and shipping cost");
	}

	@Test 
	void testTotalSingleItemStandardShipping(){
		void testTotalMultipleItemsExpressShipping() {
        Bouquet b1 = new Bouquet(
                "Bouquet A",
                "First test bouquet",
                "Test Category",
                15.0,
                true
        );
        Bouquet b2 = new Bouquet(
                "Bouquet B",
                "Second test bouquet",
                "Test Category",
                20.0,
                true);

        List<Bouquet> items = Arrays.asList(b1, b2);
        Order order = new Order(items, ShippingMethod.EXPRESS);
        double expectTotal = 15.0 + 20.0 + ShippingMethod.EXPRESS.getCost();
        assertEquals(expectTotal, order.getTotal(), 0.0001, 
        	"Total should be listing and express costs");
	}

	@Test 
	void testInitialStatusAndDeliveryStatus(){
		Bouquet b1 = new Bouquet(
				"Status Test",
				"Status test bouquet",
				"Test Category",
				12.0,
				true);

		Order order = new Order(Arrays.asList(b1), ShippingMethod.STANDARD);
		assertEquals(OrderStatus.PENDING, order.getStatus(), 
			"New orders should have the pending status");
		assertEquals(DeliveryStatus.NOT_SHIPPED, order.getDeliveryStatus(),
			"New orders should have the not shipped delivery status");
	}

}






