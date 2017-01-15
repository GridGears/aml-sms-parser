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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static at.gridgears.aml.AmlMessage.PositioningMethod.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class PositioningMethodMapperTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void resolveGNSS() {
        assertThat(PositioningMethodMapper.get("G"), is(GNSS));
    }

    @Test
    public void resolveWifiSignal() {
        assertThat(PositioningMethodMapper.get("W"), is(WIFI_SIGNAL));
    }

    @Test
    public void resolveCell() {
        assertThat(PositioningMethodMapper.get("C"), is(CELL));
    }

    @Test
    public void resolveNoLocation() {
        assertThat(PositioningMethodMapper.get("N"), is(NO_LOCATION));
    }

    @Test
    public void throwsExceptionOnUnknown() {
        thrown.expect(IllegalArgumentException.class);

        PositioningMethodMapper.get("ZZ");
    }

    @Test
    public void returnsNullOnNull() {
        assertThat(PositioningMethodMapper.get(null), nullValue());
    }
}