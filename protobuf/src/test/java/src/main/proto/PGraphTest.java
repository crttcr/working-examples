package src.main.proto;

import static org.junit.Assert.*;

import org.junit.Test;

import src.main.proto.PGraph.Builder;
import src.main.proto.Property.Type;

import org.apache.commons.codec.binary.Hex;

public class PGraphTest
{

    @Test
    public void onCreate_withEverthingBlank_thenReturnEmptyGraph()
    {
	// Arrange
	//
	PGraph pg = PGraph.newBuilder().build();
	
	// Assert
	//
	assertNotNull(pg);
    }

    // Creating a graph that looks like this
    //
    // PERSON:           PERSON:
    // BOY:              GIRL:
    // (1) ---[:KNOWS]-->(2)
    //

    @Test
    public void onCreate_withTwoNodesOneEdge_thenReturnCorrectGraph()
    {
	// Arrange
	//
	Builder   pgb = PGraph.newBuilder();
	Vertex  proto = Vertex.newBuilder().addLabel("PERSON").build();
	Vertex     v1 = proto.newBuilderForType().setId(1).addLabel("BOY").build();
	Vertex     v2 = proto.newBuilderForType().setId(2).addLabel("GIRL").build();
	Edge       e1 = Edge.newBuilder().setId(1).setFrom(1).setTo(2).setRelType("KNOWS").build();
	
	pgb.addV(v1);
	pgb.addV(v2);
	pgb.addE(e1);
	
	// Act
	//
	PGraph  pg = pgb.build();
	byte[]  bs = pg.toByteArray();
	String ser = Hex.encodeHexString(bs);
	
	// Assert
	//
	assertNotNull(pg);
	assertNotNull(ser);
	System.out.println(pg);
	System.out.println(ser);
    }


    @Test
    public void onCreate_withProperties_thenReturnCorrectGraph()
    {
	// Arrange
	//
	Builder   pgb = PGraph.newBuilder();
	Vertex  proto = Vertex.newBuilder().addLabel("PERSON").build();
	Property   p1 = Property.newBuilder().setName("Age").setType(Type.INTEGER).setValue("23").build();
	Property   p2 = Property.newBuilder().setName("Since").setType(Type.STRING).setValue("Yesterday").build();
	
	Vertex     v1 = proto.newBuilderForType().setId(1).addLabel("BOY").addP(p1).build();
	Edge       e1 = Edge.newBuilder().setId(1).setFrom(1).setTo(2).addP(p2).setRelType("KNOWS").build();
	
	pgb.addV(v1);
	pgb.addE(e1);
	
	// Act
	//
	PGraph  pg = pgb.build();
	byte[]  bs = pg.toByteArray();
	String ser = Hex.encodeHexString(bs);
	
	// Assert
	//
	assertNotNull(pg);
	assertNotNull(ser);
	System.out.println(pg);
	System.out.println(ser);
    }
}
