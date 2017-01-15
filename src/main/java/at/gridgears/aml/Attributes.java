/*
 * MIT License
 *
 * Copyright (c) 2017 GridGears
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
