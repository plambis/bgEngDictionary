package bg.plambis.data.impl;

import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.NoPublicFieldsExceptStaticFinalRule;
import com.openpojo.validation.test.impl.GetterTester;

public class WordImplTest {

	@Test
	public void testWordsImpl() {
		ValidatorBuilder validationBuilder = ValidatorBuilder.create();

		// Lets make sure that we have a getter and a setter for every field
		// defined.
		validationBuilder.with(new NoPublicFieldsExceptStaticFinalRule());
		validationBuilder.with(new GetterMustExistRule());

		// Lets also validate that they are behaving as expected
		validationBuilder.with(new GetterTester());

		// Start the Test
		Validator wordImplValidator = validationBuilder.build();

		PojoClass wordImplPojo = PojoClassFactory.getPojoClass(WordImpl.class);
		wordImplValidator.validate(wordImplPojo);
	}

}
