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
import at.gridgears.aml.exceptions.AmlValidationException;
import at.gridgears.aml.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AmlMessageParser {
    private static final String DATE_FORMAT = "yyyyMMddHHmmss";
    private static final String ATTRIBUTE_SEPARATOR = ";";
    private static final String PAIR_SEPARATOR = "=";
    private static List<String> KNOWN_ATTRIBUTE_NAMES = Arrays.asList("A\"ML","lt","lg","rd","top","lc","pm","si","ei","mcc","mnc","ml");

    private final Validator validator;


    public AmlMessageParser() {
        this(new Settings());
    }

    public AmlMessageParser(Settings settings) {
        validator = settings.getValidator();
    }

    public AmlMessage parse(final String message) throws AmlParseException, AmlValidationException {
        Map<String, Pair> pairs = Stream.of(message.split(ATTRIBUTE_SEPARATOR)).map(Pair::new).collect(Collectors.toMap(Pair::getKey, Function.identity(),(first, latest) -> latest));

        checkMessageLength(message, pairs);
        checkAttributes(pairs);

        AmlMessageBuilder builder = AmlMessageBuilder.newAdvancedMobileLocation()
                .version(getHeaderValue(pairs))
                .latitude(getLatitude(pairs))
                .longitude(getLongitude(pairs))
                .radiusMeters(getRadius(pairs))
                .imei(getImei(pairs))
                .imsi(getImsi(pairs))
                .levelOfConfidence(getLoc(pairs))
                .mcc(getMcc(pairs))
                .mnc(getMnc(pairs))
                .timeOfPositioning(getTop(pairs))
                .positionMethod(getPositioningMethod(pairs))
                 .length(getMessageLength(pairs));


        return validator.validate(builder.build());
    }

    private void checkAttributes(Map<String, Pair> pairs) throws AmlParseException {
        if(!KNOWN_ATTRIBUTE_NAMES.containsAll(pairs.keySet())) {
            Set<String> unknownAttributes = pairs.keySet();
            unknownAttributes.removeAll(KNOWN_ATTRIBUTE_NAMES);

            throw new AmlParseException("unknown attributes "+unknownAttributes);
        }
    }

    private void checkMessageLength(String message, Map<String, Pair> pairs) throws AmlParseException {
        Integer expectedMessageLength = getMessageLength(pairs);
        Integer actualMessageLength = message.length();

        if(!Objects.equals(expectedMessageLength, actualMessageLength)) {
            throw new AmlParseException("expected message length "+expectedMessageLength + " but was "+actualMessageLength);
        }
    }

    private Integer getHeaderValue(Map<String, Pair> pairs) throws AmlParseException {
        return getIntegerValue(pairs, "A\"ML");
    }

    private Double getLatitude(Map<String, Pair> pairs) throws AmlParseException {
        return getDoubleValue(pairs, "lt");
    }

    private Double getLongitude(Map<String, Pair> pairs) throws AmlParseException {
        return getDoubleValue(pairs, "lg");
    }

    private Double getRadius(Map<String, Pair> pairs) throws AmlParseException {
        return getDoubleValue(pairs, "rd");
    }

    private Date getTop(Map<String, Pair> pairs) throws AmlParseException {
        return getDateValue(pairs, "top", DATE_FORMAT);
    }

    private Integer getLoc(Map<String, Pair> pairs) throws AmlParseException {
        return getIntegerValue(pairs, "lc");
    }

    private AmlMessage.PositioningMethod getPositioningMethod(Map<String, Pair> pairs) throws AmlParseException {
        Pair pair = pairs.get("pm");
        if (Objects.nonNull(pair)) {
            AmlMessage.PositioningMethod positioningMethod = AmlMessage.PositioningMethod.get(pair.attributeValue);
            if (positioningMethod == null) {
                throw new AmlParseException("unknown position method: "+pair.attributeValue);
            } else {
                return positioningMethod;
            }
        } else {
            return null;
        }
    }

    private String getImsi(Map<String, Pair> pairs) {
        return getStringValue(pairs,"si");
    }

    private String getImei(Map<String, Pair> pairs) {
        return getStringValue(pairs, "ei");
    }

    private String getMcc(Map<String, Pair> pairs) {
        return getStringValue(pairs, "mcc");
    }

    private String getMnc(Map<String, Pair> pairs) {
        return getStringValue(pairs, "mnc");
    }

    private Integer getMessageLength(Map<String, Pair> pairs) throws AmlParseException {
        return getIntegerValue(pairs,"ml");
    }

    private String getStringValue(Map<String, Pair> pairs, String attributeName) {
        Pair pair = pairs.get(attributeName);
        if (Objects.nonNull(pair)) {
           return pair.attributeValue;
        } else {
            return null;
        }
    }

    private Integer getIntegerValue(Map<String, Pair> pairs, String attributeName) throws AmlParseException {
        Pair pair = pairs.get(attributeName);
        if (Objects.nonNull(pair)) {
            try {
                return Integer.parseInt(pair.attributeValue);
            } catch (NumberFormatException e) {
                throw new AmlParseException("could not parse int for attribute "+attributeName,e);
            }
        } else {
            return null;
        }
    }

    private Double getDoubleValue(Map<String, Pair> pairs, String attributeName) throws AmlParseException {
        Pair pair = pairs.get(attributeName);
        if (Objects.nonNull(pair)) {
            try {
                return Double.parseDouble(pair.attributeValue);
            } catch (NumberFormatException e) {
                throw new AmlParseException("could not parse double for attribute: "+attributeName, e);
            }
        } else {
            return null;
        }
    }

    private Date getDateValue(Map<String, Pair> pairs, String attributeName, String formatString) throws AmlParseException {
        Pair pair = pairs.get(attributeName);
        if (Objects.nonNull(pair)) {
            SimpleDateFormat format = new SimpleDateFormat(formatString);
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                return format.parse(pair.attributeValue);
            } catch (ParseException e) {
                throw new AmlParseException("incorrect date format for attribute "+attributeName,e);
            }
        } else {
            return null;
        }
    }

    private static class Pair {
        private String attributeName = null;
        private String attributeValue = null;

        private Pair(String pair) {
            String[] attribute = pair.split(PAIR_SEPARATOR);

            attributeName = attribute[0];

            if (attribute.length > 1) {
                attributeValue = attribute[1];
            }
        }

        String getKey() {
            return attributeName;
        }
    }
}
