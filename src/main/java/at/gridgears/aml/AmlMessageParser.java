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

import java.util.Date;
import java.util.Objects;

public class AmlMessageParser {

    private final Validator validator;

    public AmlMessageParser() {
        this(new Settings());
    }

    public AmlMessageParser(Settings settings) {
        validator = settings.getValidator();
    }

    public AmlMessage parse(final String message) throws AmlParseException, AmlValidationException {
        Attributes attributes = Attributes.parse(message);

        checkMessageLength(message, attributes);
        checkAttributes(attributes);

        AmlMessageBuilder builder = AmlMessageBuilder.newAdvancedMobileLocation()
                .version(getHeaderValue(attributes))
                .latitude(getLatitude(attributes))
                .longitude(getLongitude(attributes))
                .radiusMeters(getRadius(attributes))
                .imei(getImei(attributes))
                .imsi(getImsi(attributes))
                .levelOfConfidence(getLoc(attributes))
                .mcc(getMcc(attributes))
                .mnc(getMnc(attributes))
                .timeOfPositioning(getTop(attributes))
                .positionMethod(getPositioningMethod(attributes))
                .length(getMessageLength(attributes));


        return validator.validate(builder.build());
    }

    private void checkAttributes(Attributes attributes) throws AmlParseException {
        attributes.check();
    }

    private void checkMessageLength(String message, Attributes attributes) throws AmlParseException {
        Integer expectedMessageLength = getMessageLength(attributes);
        Integer actualMessageLength = message.length();

        if (!Objects.equals(expectedMessageLength, actualMessageLength)) {
            throw new AmlParseException("expected message length " + expectedMessageLength + " but was " + actualMessageLength);
        }
    }

    private Integer getHeaderValue(Attributes attributes) throws AmlParseException {
        return attributes.get("A\"ML", Attribute::getIntegerValue);
    }

    private Double getLatitude(Attributes attributes) throws AmlParseException {
        return attributes.get("lt", Attribute::getDoubleValue);
    }

    private Double getLongitude(Attributes attributes) throws AmlParseException {
        return attributes.get("lg", Attribute::getDoubleValue);
    }

    private Double getRadius(Attributes attributes) throws AmlParseException {
        return attributes.get("rd", Attribute::getDoubleValue);
    }

    private Date getTop(Attributes attributes) throws AmlParseException {
        return attributes.get("top", Attribute::getDateValue);
    }

    private Integer getLoc(Attributes attributes) throws AmlParseException {
        return attributes.get("lc", Attribute::getIntegerValue);
    }

    private AmlMessage.PositioningMethod getPositioningMethod(Attributes attributes) throws AmlParseException {
        return attributes.get("pm", Attribute::getPositioningMethod);
    }

    private String getImsi(Attributes attributes) throws AmlParseException {
        return attributes.get("si", Attribute::getStringValue);
    }

    private String getImei(Attributes attributes) throws AmlParseException {
        return attributes.get("ei", Attribute::getStringValue);
    }

    private String getMcc(Attributes attributes) throws AmlParseException {
        return attributes.get("mcc", Attribute::getStringValue);
    }

    private String getMnc(Attributes attributes) throws AmlParseException {
        return attributes.get("mnc", Attribute::getStringValue);
    }

    private Integer getMessageLength(Attributes attributes) throws AmlParseException {
        return attributes.get("ml", Attribute::getIntegerValue);
    }
}
