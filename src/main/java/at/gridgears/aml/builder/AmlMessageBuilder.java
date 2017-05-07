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

package at.gridgears.aml.builder;

import at.gridgears.aml.PositioningMethod;

import java.time.Instant;

public interface AmlMessageBuilder<T> {
    AmlMessageBuilder<T> version(Integer version);

    AmlMessageBuilder<T> latitude(Double latitude);

    AmlMessageBuilder<T> longitude(Double longitude);

    AmlMessageBuilder<T> radiusMeters(Double radiusMeters);

    AmlMessageBuilder<T> imsi(String imsi);

    AmlMessageBuilder<T> imei(String imei);

    AmlMessageBuilder<T> timeOfPositioning(Instant timeOfPositioning);

    AmlMessageBuilder<T> levelOfConfidence(Integer levelOfConfidence);

    AmlMessageBuilder<T> positionMethod(PositioningMethod positionMethod);

    AmlMessageBuilder<T> mcc(String mcc);

    AmlMessageBuilder<T> mnc(String mnc);

    AmlMessageBuilder<T> length(Integer length);

    T build();
}
