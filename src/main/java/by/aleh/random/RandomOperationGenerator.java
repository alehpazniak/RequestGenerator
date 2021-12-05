package by.aleh.random;

import by.aleh.domain.Operation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Component
public class RandomOperationGenerator {

    @Value("${app.operationgenerator.operation.generate.lowLimit}")
    private Double lowLimit;
    @Value("${app.operationgenerator.operation.generate.upperLimit}")
    private Double upperLimit;

    @Autowired
    private RandomOperationNameGenerator randomOperationNameGenerator;

    public Operation generate() {

        double argFirst = Math.random() * (upperLimit - lowLimit) + lowLimit;
        double secondArg = Math.random() * (upperLimit - lowLimit) + lowLimit;

        Operation operation = new Operation();

        operation.setOperationName(randomOperationNameGenerator.generate());
        operation.setArgFirst(argFirst);
        operation.setArgSecond(secondArg);
        return operation;
    }
}
