package spring.data.neo4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@NodeEntity
@EqualsAndHashCode(exclude="teammates")
public class Person
{
	@GraphId
	private Long	id;

	private String	name;

	// Empty constructor is required as of Neo4j API 2.0.5
	//
	private Person()
	{
	}

	public Person(String name)
	{
		this.name = name;
	}

	@Relationship(type = "TEAMMATE", direction = Relationship.UNDIRECTED)
	public Set<Person> teammates;

	public void worksWith(Person person)
	{
		if (teammates == null)
		{
			teammates = new HashSet<>();
		}

		teammates.add(person);
	}

	@Override
	public String toString()
	{
		return this.name + "'s teammates => "
			+ Optional.ofNullable(this.teammates).orElse(Collections.emptySet())
			.stream().map(person -> person.getName())
			.collect(Collectors.toList());
	}
}
