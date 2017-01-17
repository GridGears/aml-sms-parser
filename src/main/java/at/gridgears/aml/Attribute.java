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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.TimeZone;

class Attribute {
    private static final String SEPARATOR = "=";
    private static final String DATE_FORMAT = "yyyyMMddHHmmss";

    private final String name;
    private final String value;

    private Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    static Attribute create(String input) {
        String[] attribute = input.split(SEPARATOR);

        String name = attribute[0];

        String value;
        if (attribute.length > 1) {
            value = attribute[1];
        } else {
            value = null;
        }

        return new Attribute(name, value);
    }

    String getName() {
        return name;
    }

    String getStringValue() {
        return value;
    }

    Integer getIntegerValue() {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    Double getDoubleValue() {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    Instant getInstantValue() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return format.parse(value).toInstant();
        } catch (ParseException e) {
            return null;
        }
    }

    AmlMessage.PositioningMethod getPositioningMethod() {
        try {
            return PositioningMethodMapper.get(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
