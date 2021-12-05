package numberGenerator.schedule;

import by.aleh.domain.Operation;
import by.aleh.enumeration.OperationName;
import by.aleh.random.RandomOperationGenerator;
import by.aleh.schedule.RandomOperationSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RandomOperationSenderTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RandomOperationGenerator randomOperationGenerator;

    @InjectMocks
    private RandomOperationSender randomOperationSender;

    @Test
    void sendRandomOperation() {
        double firstArg = 2.1;
        double secondArg = 4.3;
        double result = 6.4;
        Operation additionOperation = new Operation(OperationName.ADDITION, firstArg, secondArg, result);
        when(randomOperationGenerator.generate()).thenReturn(additionOperation);

        assertDoesNotThrow(() -> randomOperationSender.sendRandomOperation());
    }
}