package spring.data.mongo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class Item
{
	@Setter(AccessLevel.NONE)
	private Long id;

	//	private Order order;
	private String product;
	private double price;
	private int quantity;
}
