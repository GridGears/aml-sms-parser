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

import at.gridgears.aml.exceptions.AmlException;
import at.gridgears.aml.exceptions.AmlValidationException;
import at.gridgears.aml.validation.DefaultValidator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.Instant;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DefaultValidatorTest {

    private DefaultAmlMessageBuilder builder;

    private DefaultValidator validator = new DefaultValidator();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() {
        builder = DefaultAmlMessageBuilder.newAdvancedMobileLocation()
                .version(1)
                .latitude(54.4)
                .longitude(12.32)
                .radiusMeters(34.2)
                .timeOfPositioning(Instant.now())
                .levelOfConfidence(85)
                .positionMethod(AmlMessage.PositioningMethod.CELL)
                .imsi("1654321654")
                .imei("65897654654")
                .mcc("234")
                .mnc("30")
                .length(110);
    }

    @Test
    public void validAdvancedMobileLocation() throws Exception {
        AmlMessage amlMessage = builder.build();

        AmlMessage result = validator.validate(amlMessage);

        assertThat(result, is(amlMessage));
    }

    @Test
    public void unknownInterfaceVersion() throws AmlException {
        AmlMessage unknownInterfaceVersion = builder.version(2).build();

        thrown.expect(AmlValidationException.class);

        validator.validate(unknownInterfaceVersion);
    }
}