package at.gridgears.aml;

import at.gridgears.aml.exceptions.AmlException;
import at.gridgears.aml.exceptions.AmlParseException;
import at.gridgears.aml.validation.Validator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AmlMessageParserTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private AmlMessageParser parser;

	@Before
	public void init() {
		this.parser = new AmlMessageParser();
	}


	@Test
	public void validAmlMessage() throws AmlException {
		String validMessage = "A\"ML=1;lt=+54.76397;lg=-0.18305;rd=50;top=20130717141935;lc=90;pm=W;si=123456789012345;ei=1234567890123456;mcc=234;mnc=30;ml=128";

		AmlMessage amlMessage = parser.parse(validMessage);

		assertThat(amlMessage.getVersion(),is(1));
		assertThat(amlMessage.getLatitude(),is(54.76397D));
		assertThat(amlMessage.getLongitude(),is(-0.18305D));
		assertThat(amlMessage.getRadiusMeters(),is(50D));
		assertThat(amlMessage.getTimeOfPositioning(),is(new Date(1374070775000L)));
		assertThat(amlMessage.getLevelOfConfidence(),is(90));
		assertThat(amlMessage.getPositionMethod(), is(AmlMessage.PositioningMethod.WIFI_SIGNAL));
		assertThat(amlMessage.getImsi(),is("123456789012345"));
		assertThat(amlMessage.getImei(),is("1234567890123456"));
		assertThat(amlMessage.getMcc(),is("234"));
		assertThat(amlMessage.getMnc(),is("30"));
	}

	@Test
	public void verifyInteractionWithValidator() throws AmlException {
		Settings settings = new Settings(mock(Validator.class));
		String validMessage = "A\"ML=1;lt=+54.76397;lg=-0.18305;rd=50;top=20130717141935;lc=90;pm=W;si=123456789012345;ei=1234567890123456;mcc=234;mnc=30;ml=128";

		new AmlMessageParser(settings).parse(validMessage);

		verify(settings.getValidator()).validate(any(AmlMessage.class));
	}


	@Test
	public void invalidMessageLength() throws AmlException {
		String invalidMessageLength = "A\"ML=1;lt=+54.76397;lg=-0.18305;rd=50;top=20130717141935;lc=90;pm=W;si=123456789012345;ei=1234567890123456;mcc=234;mnc=30;ml=10";

		thrown.expect(AmlParseException.class);

		parser.parse(invalidMessageLength);
	}

	@Test
	public void unknownAttribute() throws AmlException {
		String unknownAttribute = "A\"ML=1;lt=+54.76397;lg=-0.18305;rd=50;top=20130717141935;lc=90;pm=W;si=123456789012345;ei=1234567890123456;mcc=234;mnc=30;kl=23;ml=134";

		thrown.expect(AmlParseException.class);

		parser.parse(unknownAttribute);
	}
}
