package com.kcom.services.properties;

import com.kcom.types.Coin;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author jan.deulofeu
 */
public class PropertiesManager implements PropertiesService {

    public static final String PROPERTIES_RESOURCE = "coin-inventory.properties";

    protected static final String SEPARATOR = "=";
    protected static final String NEW_LINE = "\n";


    @Override
    public EnumMap<Coin, Integer> readProperties(final String resourceName) {

        try {
            final URI uri = new URI(getClass().getClassLoader().getResource(resourceName).toString());

            try (final Stream<String> lines = Files.lines(Paths.get(uri))) {

                final Map<Coin, Integer> collect = lines.map(line -> line.split(SEPARATOR))
                        .collect(Collectors.toMap(k -> Coin.getCoinTypeByValue(Integer.valueOf(k[0])), v -> Integer.valueOf(v[1])));

                return new EnumMap<>(collect);
            }

        } catch (final IOException | URISyntaxException e) {
            throw new RuntimeException(String.format("Could not load resource content for resource %s", resourceName));
        } catch (final NullPointerException e) {
            throw new IllegalArgumentException("Properties not found");
        }
    }


    @Override
    public void writeProperties(final EnumMap<Coin, Integer> properties, final String resourceName) {

        try {

            final URI uri = new URI(getClass().getClassLoader().getResource(resourceName).toString());
            final Path path = Paths.get(uri);

            try (final BufferedWriter writer = Files.newBufferedWriter(path)) {

                properties.entrySet().stream().forEach(k -> {

                    try {

                        final String line = new StringBuilder().append(k.getKey().getDenomination())
                                .append(SEPARATOR)
                                .append(k.getValue())
                                .append(NEW_LINE).toString();
                        writer.write(line);

                    } catch (final IOException e) {
                        throw new RuntimeException(String.format("Error persisting %s to resource %s", k, resourceName));
                    }
                });
            }

        } catch (final IOException | URISyntaxException e) {
            throw new RuntimeException(String.format("Could not load resource content for resource %s", resourceName));
        } catch (final NullPointerException e) {
            throw new IllegalArgumentException("Properties not found");
        }
    }
}
