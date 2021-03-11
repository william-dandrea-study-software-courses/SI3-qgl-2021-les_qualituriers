package fr.unice.polytech.si3.qgl.qualituriers.engine.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntities;

import java.io.IOException;

public class BoatEntitySerializer extends StdSerializer<BoatEntities> {

    public BoatEntitySerializer() {
        this(null);
    }

    protected BoatEntitySerializer(Class<BoatEntities> t) {
        super(t);
    }

    @Override
    public void serialize(BoatEntities value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.getType());
    }
}
