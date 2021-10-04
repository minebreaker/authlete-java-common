import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class ListUp {

    /**
     * This script generates authlete-reflect-config.json
     * which is passed as a parameter for -H:ReflectionConfigurationFiles option
     * when generating a native binary.
     */
    public static void main(String[] args) throws Exception {

        Path path = Paths.get("./src/main/java/com/authlete");
        Path out = Paths.get("./authlete-reflect-config.json");

        try (Stream<Path> s = Files.walk(path); BufferedWriter w = Files.newBufferedWriter(out, StandardCharsets.UTF_8)) {
            w.write("[\n");

            w.write(s
                    .filter(Files::isRegularFile)
                    .filter(p -> !Objects.equals(p.getFileName().toString(), "package-info.java"))
                    .map(p -> {
                        try {
                            String target = p.toString()
                                    .replace("./src/main/java/", "")
                                    .replace(".java", "")
                                    .replaceAll("/", ".");
                            System.out.println(target);
                            Class<?> c = ClassLoader.getSystemClassLoader().loadClass(target);

                            return "{\"name\":\"" +
                                    c.getCanonicalName() +
                                    "\",\n" +
                                    "\"allDeclaredConstructors\" : true,\n" +
                                    "    \"allPublicConstructors\" : true,\n" +
                                    "    \"allDeclaredMethods\" : true,\n" +
                                    "    \"allPublicMethods\" : true,\n" +
                                    "    \"allDeclaredClasses\" : true,\n" +
                                    "    \"allPublicClasses\" : true\n" +
                                    "}";
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(joining(",\n")));
            w.write("]");
        }
    }
}
