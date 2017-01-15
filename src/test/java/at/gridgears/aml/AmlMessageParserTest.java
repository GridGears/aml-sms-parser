package at.gridgears.aml;

import at.gridgears.aml.exceptions.AmlException;
import at.gridgears.aml.exceptions.AmlParseException;
import at.gridgears.aml.exceptions.AmlValidationException;
import at.gridgears.aml.validation.Validator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static org.hamcrest.CoreMatchers.nullValue;
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
		this.parser = new AmlMessageParser(new Settings(new NoValidation()));
	}


	@Test
	public void validAmlMessageWithDefaultValidation() throws AmlException {
		String validMessage = "A\"ML=1;lt=+54.76397;lg=-0.18305;rd=50;top=20130717141935;lc=90;pm=W;si=123456789012345;ei=1234567890123456;mcc=234;mnc=30;ml=128";

		AmlMessage amlMessage = new AmlMessageParser().parse(validMessage);

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
		assertThat(amlMessage.getLength(),is(128));
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

	@Test
	public void incorrectDateFormat() throws AmlException {
		String invalidDateFormat = "A\"ML=1;lt=+54.76397;lg=-0.18305;rd=50;top=20130717;lc=90;pm=W;si=123456789012345;ei=1234567890123456;mcc=234;mnc=30;ml=122";

		thrown.expect(AmlParseException.class);

		parser.parse(invalidDateFormat);
	}

	@Test
	public void unknownPositioningMethod() throws AmlException {
		String unknownPositioningMethod = "A\"ML=1;lt=+54.76397;lg=-0.18305;rd=50;top=20130717141935;lc=90;pm=Z;si=123456789012345;ei=1234567890123456;mcc=234;mnc=30;ml=128";

		thrown.expect(AmlParseException.class);

		parser.parse(unknownPositioningMethod);
	}

	@Test
	public void invalidLatitude() throws AmlException {
		String invalidLatitude = "A\"ML=1;lt=+g4.7d397;lg=-0.18305;rd=50;top=20130717141935;lc=90;pm=W;si=123456789012345;ei=1234567890123456;mcc=234;mnc=30;ml=128";

		thrown.expect(AmlParseException.class);

		parser.parse(invalidLatitude);
	}

	@Test
	public void invalidLevelOfConfidence() throws AmlException {
		String invalidLevelOfConfidence = "A\"ML=1;lt=+54.76397;lg=-0.18305;rd=50;top=20130717141935;lc=99.9;pm=W;si=123456789012345;ei=1234567890123456;mcc=234;mnc=30;ml=130";

		thrown.expect(AmlParseException.class);

		parser.parse(invalidLevelOfConfidence);
	}

	@Test
	public void noHeaderValue() throws AmlException {
		String noHeaderValue = "lt=+54.76397;lg=-0.18305;rd=50;top=20130717141935;lc=90;pm=W;si=123456789012345;ei=1234567890123456;mcc=234;mnc=30;ml=121";

		AmlMessage message = parser.parse(noHeaderValue);

		assertThat(message.getVersion(), nullValue());
	}

	@Test
	public void noLatitude() throws AmlException {
		String noLatitude = "A\"ML=1;lg=-0.18305;rd=50;top=20130717141935;lc=90;pm=W;si=123456789012345;ei=1234567890123456;mcc=234;mnc=30;ml=115";

		AmlMessage message = parser.parse(noLatitude);

		assertThat(message.getLatitude(), nullValue());
	}

	@Test
	public void noPositioningMethod() throws AmlException {
		String noPositionMethod = "A\"ML=1;lt=+54.76397;lg=-0.18305;rd=50;top=20130717141935;lc=90;si=123456789012345;ei=1234567890123456;mcc=234;mnc=30;ml=123";

		AmlMessage message = parser.parse(noPositionMethod);

		assertThat(message.getPositionMethod(), nullValue());
	}

	@Test
	public void noImsi() throws AmlException {
		String noImsi = "A\"ML=1;lt=+54.76397;lg=-0.18305;rd=50;top=20130717141935;lc=90;pm=W;ei=1234567890123456;mcc=234;mnc=30;ml=109";

		AmlMessage message = parser.parse(noImsi);

		assertThat(message.getImsi(), nullValue());
	}

	@Test
	public void noTimeOfPositioning() throws AmlException {
		String noTimeOfPositioning = "A\"ML=1;lt=+54.76397;lg=-0.18305;rd=50;lc=90;pm=W;si=123456789012345;ei=1234567890123456;mcc=234;mnc=30;ml=109";

		AmlMessage message = parser.parse(noTimeOfPositioning);

		assertThat(message.getTimeOfPositioning(), nullValue());
	}

	@Test
    public void invalidAttributePair() throws AmlException {
	    String invalidAttribute = "A\"ML=1;a==;lt=+54.76397;lg=-0.18305;rd=50;top=20130717141935;lc=90;pm=W;si=123456789012345;ei=1234567890123456;mcc=234;mnc=30;ml=132";

	    thrown.expect(AmlParseException.class);

	    parser.parse(invalidAttribute);
    }


    @Test
    public void invalidAttribute() throws AmlException {
        String invalidAttribute = "A\"ML=1;;;;lt=+54.76397;lg=-0.18305;rd=50;top=20130717141935;lc=90;pm=W;si=123456789012345;ei=1234567890123456;mcc=234;mnc=30;ml=131";

        thrown.expect(AmlParseException.class);

        parser.parse(invalidAttribute);
    }



    private static class NoValidation implements Validator {

		@Override
		public AmlMessage validate(AmlMessage message) throws AmlValidationException {
			return message;
		}
	}
}
