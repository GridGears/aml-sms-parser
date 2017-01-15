package at.gridgears.aml;


import at.gridgears.aml.exceptions.AmlParseException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class Attributes {
    private static final List<String> KNOWN_ATTRIBUTE_NAMES = Arrays.asList("A\"ML", "lt", "lg", "rd", "top", "lc", "pm", "si", "ei", "mcc", "mnc", "ml");

    private static final String ATTRIBUTE_SEPARATOR = ";";

    private final Map<String, Attribute> attributes;

    private Attributes(Map<String, Attribute> attributes) {
        this.attributes = Collections.unmodifiableMap(new HashMap<>(attributes));
    }

    static Attributes parse(String message) {
        Map<String, Attribute> attributes = Stream.of(message.split(ATTRIBUTE_SEPARATOR)).map(Attribute::create).collect(Collectors.toMap(Attribute::getName, Function.identity(), (first, latest) -> latest));
        return new Attributes(attributes);
    }

    void check() throws AmlParseException {
        if (!KNOWN_ATTRIBUTE_NAMES.containsAll(attributes.keySet())) {
            Set<String> unknownAttributes = new HashSet<>(attributes.keySet());
            unknownAttributes.removeAll(KNOWN_ATTRIBUTE_NAMES);

            throw new AmlParseException("unknown attributes " + unknownAttributes);
        }
    }

    <T> T get(String attributeName, Function<Attribute, T> function) throws AmlParseException {
        Attribute attribute = attributes.get(attributeName);

        T result;

        if (attribute != null) {
            result = function.apply(attribute);
            if (result == null) {
                throw new AmlParseException("Could not parse " + attribute);
            }
        } else {
            result = null;
        }
        return result;
    }
}
