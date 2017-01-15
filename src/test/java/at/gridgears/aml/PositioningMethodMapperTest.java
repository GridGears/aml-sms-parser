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