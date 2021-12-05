package numberGenerator.random;

import by.aleh.enumeration.OperationName;
import by.aleh.random.RandomOperationNameGenerator;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RandomOperationNameGeneratorTest {

    @Autowired
    private RandomOperationNameGenerator randomOperationNameGenerator;

    @Test
    void rangeNumber100PercentsDivTest() {

        var operationRange = new RandomOperationNameGenerator();
        operationRange.setDivideRate(100);
        operationRange.setAdditionRate(0);
        operationRange.setMultiplyRate(0);
        operationRange.setSubtractRate(0);

        for (int i = 0; i < 100; i++) {
            assertSame(operationRange.generate(), OperationName.DIVIDE);
        }
    }

    @Test
    void shouldTestExceptionInMethodValidateRateWhenAmountNot100Percent() {

        var operationRange = new RandomOperationNameGenerator();
        operationRange.setAdditionRate(25);
        operationRange.setSubtractRate(25);
        operationRange.setMultiplyRate(25);
        operationRange.setDivideRate(20);

        Exception exception = assertThrows(IllegalStateException.class, operationRange::validateRate);
        assertTrue(exception.getMessage().startsWith("Sum of all rates must be 100%"));
    }

    @Test
    void shouldTestValidateRateFotAmount100Percent() {

        var operationRange = new RandomOperationNameGenerator();
        operationRange.setAdditionRate(25);
        operationRange.setSubtractRate(25);
        operationRange.setMultiplyRate(25);
        operationRange.setDivideRate(25);

        //todo: assert not throws
        operationRange.validateRate();
    }

    @Test
    void shouldTestGenerateOperationNameWithProbabilityBySwitch() {
        //given
        var operationRange = new RandomOperationNameGenerator();

        int probabilityAddition = 10;
        int probabilitySubtract = 20;
        int probabilityMultiply = 30;
        int probabilityDivide = 40;

        operationRange.setAdditionRate(probabilityAddition);
        operationRange.setSubtractRate(probabilitySubtract);
        operationRange.setMultiplyRate(probabilityMultiply);
        operationRange.setDivideRate(probabilityDivide);

        int countDivide = 0;
        int countAddition = 0;
        int countSubtract = 0;
        int countMultiply = 0;

        int numberOfSamples = 1000;

        //when
        for (int i = 0; i < numberOfSamples; i++) {
            OperationName operationName = operationRange.generate();
            switch (operationName) {
                case ADDITION:
                    ++countAddition;
                    break;
                case DIVIDE:
                    ++countDivide;
                    break;
                case SUBTRACT:
                    ++countSubtract;
                    break;
                case MULTIPLY:
                    ++countMultiply;
                    break;
            }
        }
        int percentProbabilityAddition = countAddition * 100 / 1000;
        int percentProbabilityDivide = countDivide * 100 / 1000;
        int percentProbabilitySubtract = countSubtract * 100 / 1000;
        int percentProbabilityMultiply = countMultiply * 100 / 1000;

        //then
        assertThat(percentProbabilityAddition).isCloseTo(probabilityAddition, Offset.offset(5));
        assertThat(percentProbabilityDivide).isCloseTo(probabilityDivide, Offset.offset(5));
        assertThat(percentProbabilitySubtract).isCloseTo(probabilitySubtract, Offset.offset(5));
        assertThat(percentProbabilityMultiply).isCloseTo(probabilityMultiply, Offset.offset(5));
    }

    @Test
    void shouldTestGenerateOperationNameWithProbabilityByMap() {
        //given
        var operationRange = new RandomOperationNameGenerator();

        int probabilityAddition = 10;
        int probabilitySubtract = 20;
        int probabilityMultiply = 30;
        int probabilityDivide = 40;

        operationRange.setAdditionRate(probabilityAddition);
        operationRange.setSubtractRate(probabilitySubtract);
        operationRange.setMultiplyRate(probabilityMultiply);
        operationRange.setDivideRate(probabilityDivide);

        Map<OperationName, Integer> map = new HashMap<>();

        for (int i = 0; i < 1000; i++) {
            OperationName operationName = operationRange.generate();
            if (map.containsKey(operationName)) {
                Integer count = map.get(operationName);
                map.put(operationName, ++count);
            } else {
                map.put(operationName, 1);
            }
        }

        int percentProbabilityAddition = map.get(OperationName.ADDITION) * 100 / 1000;
        int percentProbabilitySubtract = map.get(OperationName.SUBTRACT) * 100 / 1000;
        int percentProbabilityMultiply = map.get(OperationName.MULTIPLY) * 100 / 1000;
        int percentProbabilityDivide = map.get(OperationName.DIVIDE) * 100 / 1000;

        assertThat(percentProbabilityAddition).isCloseTo(probabilityAddition, Offset.offset(5));
        assertThat(percentProbabilityDivide).isCloseTo(probabilityDivide, Offset.offset(5));
        assertThat(percentProbabilitySubtract).isCloseTo(probabilitySubtract, Offset.offset(5));
        assertThat(percentProbabilityMultiply).isCloseTo(probabilityMultiply, Offset.offset(5));

        map.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " " + entry.getValue() * 100 / 1000);
        });
    }

    @Test
    void shouldTestInjectionPercentFromApplicationProperties() {

        assertEquals(10, randomOperationNameGenerator.getAdditionRate());
        assertEquals(20, randomOperationNameGenerator.getMultiplyRate());
        assertEquals(30, randomOperationNameGenerator.getSubtractRate());
        assertEquals(40, randomOperationNameGenerator.getDivideRate());
    }
}