package at.gridgears.aml;

import java.util.Objects;

import static at.gridgears.aml.AmlMessage.PositioningMethod.*;

final class PositioningMethodMapper {

    private PositioningMethodMapper() {
        //must not be instantiated
    }

    static AmlMessage.PositioningMethod get(final String pm) {
        if (Objects.isNull(pm)) {
            return null;
        }

        switch (pm) {
            case "G":
                return GNSS;
            case "W":
                return WIFI_SIGNAL;
            case "C":
                return CELL;
            case "N":
                return NO_LOCATION;
            default:
                throw new IllegalArgumentException("Cannot map PositioningMethod " + pm);
        }
    }

}
