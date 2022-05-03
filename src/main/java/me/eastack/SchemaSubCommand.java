package me.eastack;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.parser.core.models.ParseOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.eastack.constant.Version;
import picocli.CommandLine;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@CommandLine.Command(name = "schema",
        version = Version.VERSION)
public class SchemaSubCommand implements Runnable {
    @CommandLine.Option(names = {"-n", "--name"}, description = "Schema name")
    String name;

    @CommandLine.Option(names = {"-p", "--pack"}, description = "Package name")
    String pack;

    @CommandLine.Option(names = {"-d", "--dest"}, description = "Destination directory")
    String dest;

    @CommandLine.Parameters
    private String openapi;

    @Override
    public void run() {
        ParseOptions parseOptions = new ParseOptions();
        OpenAPI openAPI = new OpenAPIParser()
                .readLocation(openapi, null, parseOptions)
                .getOpenAPI();

        openAPI.getComponents()
                .getSchemas()
                .forEach((schemaName, schemaBody) -> {
                    ArrayList<FieldSpec> fieldSpecs = new ArrayList<>();
                    Map<?, ?> properties = schemaBody.getProperties();
                    properties.forEach((k, v) -> {
                        Class<?> classType = Object.class;
                        Schema<?> a = null;

                        if (v instanceof IntegerSchema integerSchema) {
                            a = integerSchema;
                            classType = Integer.class;
                        } else if (v instanceof StringSchema) {
                            classType = String.class;
                        } else if (v instanceof BooleanSchema) {
                            classType = Boolean.class;
                        } else if (v instanceof DateTimeSchema) {
                            classType = LocalDateTime.class;
                        }

                        FieldSpec build = FieldSpec.builder(classType, k.toString())
                                .addModifiers(Modifier.PUBLIC)
                                .addJavadoc(Optional.ofNullable(a).map(Schema::getDescription).orElse(""))
                                .build();
                        fieldSpecs.add(build);
                    });

                    TypeSpec build = TypeSpec.classBuilder(schemaName)
                            .addAnnotation(Data.class)
                            .addAnnotation(AllArgsConstructor.class)
                            .addAnnotation(NoArgsConstructor.class)
                            .addFields(fieldSpecs).build();
                    try {
                        buildModel(pack, build)
                                .writeTo(Path.of(dest));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        System.out.println("Generating schema...");
    }

    private static JavaFile buildModel(String packageName, TypeSpec typeSpec) {
        return JavaFile.builder(packageName, typeSpec).build();
    }
}
