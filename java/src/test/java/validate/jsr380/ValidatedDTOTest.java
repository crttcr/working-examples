package validate.jsr380;

import static java.lang.annotation.ElementType.FIELD;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.defs.NotNullDef;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatedDTOTest
{
	private static Validator validator;
	private ValidatedDTO subject;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}


	@Test
	public void testValidationsPassing()
	{
		// Arrange
		//
		subject = getPopulatedEmail();

		// Act
		//
		Set<ConstraintViolation<ValidatedDTO>> violations = validator.validate( subject );


		// Assert
		//
		assertEquals(0, violations.size());
	}


	@Test
	public void testStringViolatesNull()
	{
		// Arrange
		//
		subject = getPopulatedEmail();
		subject.setName(null);

		// Act
		//
		Set<ConstraintViolation<ValidatedDTO>> violations = validator.validate( subject );


		// Assert
		//
		assertEquals(1, violations.size());
		assertEquals( "may not be empty", violations.iterator().next().getMessage());
	}


	@Test
	public void testBorkedEmail()
	{
		// Arrange
		//
		subject = getPopulatedEmail();
		subject.setEmail("canary@bird@.com");

		// Act
		//
		Set<ConstraintViolation<ValidatedDTO>> violations = validator.validate( subject );


		// Assert
		//
		assertEquals(1, violations.size());
		assertEquals( "not a well-formed email address", violations.iterator().next().getMessage());
	}

	@Test
	public void testLongViolatesMin()
	{
		// Arrange
		//
		subject = getPopulatedEmail();
		subject.setId(0);

		// Act
		//
		Set<ConstraintViolation<ValidatedDTO>> violations = validator.validate( subject );


		// Assert
		//
		assertEquals(1, violations.size());
		assertEquals(0L, violations.iterator().next().getInvalidValue());
	}

	@Test
	public void testMultipleViolations()
	{
		// Arrange
		//
		subject = getPopulatedEmail();
		subject.setId(-10L);
		subject.setName("  ");
		subject.setEmail("www.email.com");

		// Act
		//
		Set<ConstraintViolation<ValidatedDTO>> violations = validator.validate( subject );

		// Assert
		//
		assertEquals(3, violations.size());
	}

	@Test
	public void testSettingValidationProgrammatically()
	{
		// Arrange
		//
		HibernateValidatorConfiguration configuration = Validation
		.byProvider( HibernateValidator.class )
		.configure();

		ConstraintMapping mapping = configuration.createConstraintMapping();

		mapping
		.type( ValidatedDTO.class )
		.property( "email", FIELD )
		.constraint( new NotNullDef() );

		Validator v = configuration.addMapping( mapping )
		.buildValidatorFactory()
		.getValidator();

		subject = getPopulatedEmail();
		subject.setEmail(null);

		// Act
		//
		Set<ConstraintViolation<ValidatedDTO>> violations = v.validate( subject );

		// Assert
		//
		assertEquals(1, violations.size());
	}


	////////////////////////////////////
	// Helper Methods                 //
	////////////////////////////////////

	private ValidatedDTO getPopulatedEmail()
	{
		ValidatedDTO rv = new ValidatedDTO();

		rv.setId(101);
		rv.setName("Joe Dirt");
		rv.setEmail("joe@email.com");

		return rv;
	}


}
