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

import java.time.Instant;

public final class DefaultAmlMessageBuilder implements AmlMessageBuilder<AmlMessage>{
    private Integer version;
    private Double latitude;
    private Double longitude;
    private Double radiusMeters;
    private String imsi;
    private String imei;
    private Instant timeOfPositioning;
    private Integer levelOfConfidence;
    private AmlMessage.PositioningMethod positionMethod;
    private String mcc;
    private String mnc;
    private Integer length;

    private DefaultAmlMessageBuilder() {
    }

    public static DefaultAmlMessageBuilder newAdvancedMobileLocation() {
        return new DefaultAmlMessageBuilder();
    }

    @Override
    public DefaultAmlMessageBuilder version(Integer version) {
        this.version = version;
        return this;
    }

    @Override
    public DefaultAmlMessageBuilder latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    @Override
    public DefaultAmlMessageBuilder longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    @Override
    public DefaultAmlMessageBuilder radiusMeters(Double radiusMeters) {
        this.radiusMeters = radiusMeters;
        return this;
    }

    @Override
    public DefaultAmlMessageBuilder imsi(String imsi) {
        this.imsi = imsi;
        return this;
    }

    @Override
    public DefaultAmlMessageBuilder imei(String imei) {
        this.imei = imei;
        return this;
    }

    @Override
    public DefaultAmlMessageBuilder timeOfPositioning(Instant timeOfPositioning) {
        this.timeOfPositioning = timeOfPositioning;
        return this;
    }

    @Override
    public DefaultAmlMessageBuilder levelOfConfidence(Integer levelOfConfidence) {
        this.levelOfConfidence = levelOfConfidence;
        return this;
    }

    @Override
    public DefaultAmlMessageBuilder positionMethod(AmlMessage.PositioningMethod positionMethod) {
        this.positionMethod = positionMethod;
        return this;
    }

    @Override
    public DefaultAmlMessageBuilder mcc(String mcc) {
        this.mcc = mcc;
        return this;
    }

    @Override
    public DefaultAmlMessageBuilder mnc(String mnc) {
        this.mnc = mnc;
        return this;
    }

    @Override
    public DefaultAmlMessageBuilder length(Integer length) {
        this.length = length;
        return this;
    }

    @Override
    public AmlMessage build() {
        return new AmlMessage(version, latitude, longitude, radiusMeters, imsi, imei, timeOfPositioning, levelOfConfidence, positionMethod, mcc, mnc, length);
    }
}
