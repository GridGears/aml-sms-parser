package at.gridgears.aml;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

class Attribute {
    private static final String SEPARATOR = "=";
    private static final String DATE_FORMAT = "yyyyMMddHHmmss";

    private final String name;
    private final String value;

    Attribute(String name, String value) {
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

    Date getDateValue() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return format.parse(value);
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
