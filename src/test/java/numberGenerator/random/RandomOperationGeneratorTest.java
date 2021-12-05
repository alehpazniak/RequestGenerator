package numberGenerator.random;

import by.aleh.domain.Operation;
import by.aleh.random.RandomOperationGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RandomOperationGeneratorTest {

    @Autowired
    RandomOperationGenerator randomOperationGenerator;

    @Test
    void shouldGenerate() {
        Operation operation = randomOperationGenerator.generate();

        assertNotNull(operation);
        assertNotNull(operation.getOperationName());
        assertNotNull(operation.getArgFirst());
        assertNotNull(operation.getArgSecond());
    }

    @Test
    void shouldTestInjectionUpperLimitAndLowLimitFromApplicationProperties() {
        assertEquals(1.0, randomOperationGenerator.getLowLimit());
        assertEquals(50.0, randomOperationGenerator.getUpperLimit());
    }
}