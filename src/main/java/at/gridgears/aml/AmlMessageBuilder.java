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

final class AmlMessageBuilder {
    private Integer version;
    private Double latitude;
    private Double longitude;
    private Double radiusMeters;
    private String imsi;
    private String imei;
    private Date timeOfPositioning;
    private Integer levelOfConfidence;
    private AmlMessage.PositioningMethod positionMethod;
    private String mcc;
    private String mnc;
    private Integer length;

    private AmlMessageBuilder() {
    }

    static AmlMessageBuilder newAdvancedMobileLocation() {
        return new AmlMessageBuilder();
    }

    AmlMessageBuilder version(Integer version) {
        this.version = version;
        return this;
    }

    AmlMessageBuilder latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    AmlMessageBuilder longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    AmlMessageBuilder radiusMeters(Double radiusMeters) {
        this.radiusMeters = radiusMeters;
        return this;
    }

    AmlMessageBuilder imsi(String imsi) {
        this.imsi = imsi;
        return this;
    }

    AmlMessageBuilder imei(String imei) {
        this.imei = imei;
        return this;
    }

    AmlMessageBuilder timeOfPositioning(Date timeOfPositioning) {
        this.timeOfPositioning = timeOfPositioning;
        return this;
    }

    AmlMessageBuilder levelOfConfidence(Integer levelOfConfidence) {
        this.levelOfConfidence = levelOfConfidence;
        return this;
    }

    AmlMessageBuilder positionMethod(AmlMessage.PositioningMethod positionMethod) {
        this.positionMethod = positionMethod;
        return this;
    }

    AmlMessageBuilder mcc(String mcc) {
        this.mcc = mcc;
        return this;
    }

    AmlMessageBuilder mnc(String mnc) {
        this.mnc = mnc;
        return this;
    }

    AmlMessageBuilder length(Integer length) {
        this.length = length;
        return this;
    }

    AmlMessage build() {
        return new AmlMessage(version, latitude, longitude, radiusMeters, imsi, imei, timeOfPositioning, levelOfConfidence, positionMethod, mcc, mnc, length);
    }
}
