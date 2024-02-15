package com.artysh.rubikscube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws IOException {
        /*JsonFactory jsonFactory = new JsonFactory();
        StringWriter writer = new StringWriter(); // Output will be written to StringWriter
        JsonGenerator jsonGenerator = jsonFactory.createGenerator(writer);

        jsonGenerator.writeStartObject(); // Start writing a JSON object
        jsonGenerator.writeStringField("name", "John Doe");
        jsonGenerator.writeNumberField("age", 30);
        jsonGenerator.writeBooleanField("isStudent", false);

        jsonGenerator.writeFieldName("hobbies");
        jsonGenerator.writeStartArray(); // Start writing a JSON array
        jsonGenerator.writeString("reading");
        jsonGenerator.writeString("painting");
        jsonGenerator.writeString("coding");
        jsonGenerator.writeEndArray(); // End the JSON array

        jsonGenerator.writeEndObject(); // End the JSON object



        jsonGenerator.flush();
        jsonGenerator.close();
        String json = writer.toString();
        System.out.println(json);*/
        SpringApplication.run(Main.class, args);
    }


}
