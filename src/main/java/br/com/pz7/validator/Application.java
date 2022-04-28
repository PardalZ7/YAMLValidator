import br.com.pz7.validator.validator.YAMLSchemaValidator;

import java.io.File;

public class Application {

    private static final String schemaPath = "src\\main\\java\\br\\com\\pz7\\validator\\schemas\\";
    private static final String yamlPath = "src\\main\\java\\br\\com\\pz7\\validator\\yaml\\";

    public static void main(String[] args) {

        YAMLSchemaValidator validator = new YAMLSchemaValidator();

        System.out.println("AGAINST emptySchema.json");
        validator.validateDir(getYamlPath(), getEmptySchema());

        System.out.println("\nAGAINST someSchema.json");
        validator.validateDir(getYamlPath(), getSomeSchema());
        
    }

    private static File getEmptySchema() {
        return new File(schemaPath + "emptySchema.json");
    }

    private static File getSomeSchema() {
        return new File(schemaPath + "someSchema.json");
    }

    private static File getYamlPath() {
        return new File(yamlPath);
    }

    private static File getGoodYaml() {
        return new File(yamlPath + "goodYaml.yaml");
    }

    private static File getMissingFieldYaml() {
        return new File(yamlPath + "missingFieldYaml.yaml");
    }

    private static File getBrokenYaml() {
        return new File(yamlPath + "brokenYaml.yaml");
    }

}
