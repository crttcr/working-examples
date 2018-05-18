package we.proto.util;

import com.google.protobuf.InvalidProtocolBufferException;

public class ProtobufErrorElf
{
    public static void displayError(InvalidProtocolBufferException e)
    {
	System.err.println("Error parsing protobuf source: " + e.getLocalizedMessage());
    }

}
