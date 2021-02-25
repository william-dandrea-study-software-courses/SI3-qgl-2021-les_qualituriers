package engine.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shapes;

import java.io.IOException;

public class RectangleSerializer extends StdSerializer<Rectangle> {

    public RectangleSerializer() {
        this(null);
    }

    protected RectangleSerializer(Class<Rectangle> t) {
        super(t);
    }

    @Override
    public void serializeWithType(Rectangle value, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("type", Shapes.RECTANGLE.getType());
        gen.writeNumberField("width", value.getWidth());
        gen.writeNumberField("height", value.getHeight());
        gen.writeNumberField("orientation", value.getOrientation());
        gen.writeEndObject();
    }

    @Override
    public void serialize(Rectangle rect, JsonGenerator jsonGen, SerializerProvider serializerProvider) throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField("type", Shapes.RECTANGLE.getType());
        jsonGen.writeNumberField("width", rect.getWidth());
        jsonGen.writeNumberField("height", rect.getHeight());
        jsonGen.writeNumberField("orientation", rect.getOrientation());
        jsonGen.writeEndObject();
    }
}
