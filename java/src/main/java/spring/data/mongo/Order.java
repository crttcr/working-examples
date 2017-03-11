package spring.data.mongo;

import java.util.Collection;
import java.util.LinkedHashSet;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Document
@NoArgsConstructor
public class Order
{
	@Id
	@Setter(AccessLevel.NONE)
	private String id;

	@Field("client")
	private String customer;

	private String type;

	private Collection<Item> items = new LinkedHashSet<>();
}
