package by.aleh.random;

import by.aleh.enumeration.OperationName;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

@Component
@Getter
@Setter
public class RandomOperationNameGenerator {
    private static final Random RANDOM = new Random();

    @Value("${app.operationgenerator.operation.rate.DIVIDE}")
    private Integer divideRate;
    @Value("${app.operationgenerator.operation.rate.SUBTRACT}")
    private Integer subtractRate;
    @Value("${app.operationgenerator.operation.rate.ADDITION}")
    private Integer additionRate;
    @Value("${app.operationgenerator.operation.rate.MULTIPLY}")
    private Integer multiplyRate;


    @PostConstruct
    public void validateRate() {
        if (divideRate + subtractRate + additionRate + multiplyRate != 100) {
            throw new IllegalStateException("Sum of all rates must be 100%");
        }
    }

    public OperationName generate() {
        int randomNum = RANDOM.nextInt(100);
        if (randomNum <= divideRate) {
            return OperationName.DIVIDE;
        } else if (randomNum <= divideRate + subtractRate) {
            return OperationName.SUBTRACT;
        } else if (randomNum <= (divideRate + subtractRate + additionRate)) {
            return OperationName.ADDITION;
        } else {
            return OperationName.MULTIPLY;
        }
    }

}
