package br.com.pz7.validator.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Set;

public class YAMLSchemaValidator {

    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    JsonSchemaFactory factory = JsonSchemaFactory.builder(
                    JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7)
            )
            .objectMapper(mapper)
            .build();

    public boolean validateDir (File directory, File schema) {

        boolean result = true;
        for (File file : Objects.requireNonNull(directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".yaml")
                || name.toLowerCase().endsWith(".yml")))) {

            if (!this.validate(file, schema))
                result = false;

        }

        return result;

    }

    public boolean validate (File yamlFile, File schema) {

        System.out.print("Validating " + yamlFile.getName() + "...");

        if (!yamlFile.exists()) {
            System.out.println("Invalid YAML file\n\t" + yamlFile.getAbsolutePath());
            return false;
        }

        Set<ValidationMessage> validations = null;
        try {

            validations = factory.getSchema(this.getText(schema))
                    .validate(mapper.readTree(this.getText(yamlFile)));

        } catch (Exception ex) {
            System.out.println("Invalid YAML file\n\t" + ex.getMessage());
            return false;
        }

        if (validations.size() <= 0) {

            System.out.println("Accepted");
            return true;

        } else {

            System.out.println("Schema validation failed");
            for (ValidationMessage message : validations)
                System.out.println("\t" + message.getMessage());
            return false;

        }

    }

    private String getText(File file) throws IOException {

        return new String(Files.readAllBytes(file.toPath()));

    }

}
