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

import java.util.Date;

public final class AmlMessage {

    private Integer version;

    private Double latitude;

    private Double longitude;

    private Double radiusMeters;

    private String imsi;

    private String imei;

    private Date timeOfPositioning;

    private Integer levelOfConfidence;

    private PositioningMethod positionMethod;

    private String mcc;

    private String mnc;

    private Integer length;

    AmlMessage(Integer version, double latitude, double longitude, double radiusMeters, String imsi, String imei, Date timeOfPositioning, int levelOfConfidence, PositioningMethod positionMethod, String mcc, String mnc, Integer length) {
        this.version = version;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radiusMeters = radiusMeters;
        this.imsi = imsi;
        this.imei = imei;
        this.timeOfPositioning = timeOfPositioning;
        this.levelOfConfidence = levelOfConfidence;
        this.positionMethod = positionMethod;
        this.mcc = mcc;
        this.mnc = mnc;
        this.length = length;
    }

    public Integer getVersion() {
        return version;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getRadiusMeters() {
        return radiusMeters;
    }

    public String getImsi() {
        return imsi;
    }

    public String getImei() {
        return imei;
    }

    public Date getTimeOfPositioning() {
        return timeOfPositioning;
    }

    public Integer getLevelOfConfidence() {
        return levelOfConfidence;
    }

    public PositioningMethod getPositionMethod() {
        return positionMethod;
    }

    public String getMcc() {
        return mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public Integer getLength() {
        return length;
    }

    public enum PositioningMethod {
        GNSS,
        WIFI_SIGNAL,
        CELL,
        NO_LOCATION;

        public static PositioningMethod get(final String pm) {
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
                    return null;
            }
        }
    }
}