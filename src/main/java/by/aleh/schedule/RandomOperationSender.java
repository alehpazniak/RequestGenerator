package by.aleh.schedule;

import by.aleh.domain.Operation;
import by.aleh.random.RandomOperationGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RandomOperationSender {

    private static final String SQL_CALCULATOR_URL = "http://localhost:8080/api/calculator";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RandomOperationGenerator generator;

    @Scheduled(fixedRateString = "${app.operationgenerator.random-operation-sender.fixed-rate}")
    public void sendRandomOperation() {
        String operationURL = "";

        Operation operation = generator.generate();
        switch (operation.getOperationName()) {
            case ADDITION:
                operationURL = "/addition";
                break;
            case SUBTRACT:
                operationURL = "/subtract";
                break;
            case MULTIPLY:
                operationURL = "/multiply";
                break;
            case DIVIDE:
                operationURL = "/divide";
                break;
        }

        String requestURL = SQL_CALCULATOR_URL + operationURL + "/{firstArg}/{secondArg}";

        System.out.println("Sending request to " + requestURL + " with operation " + operation.getOperationName());
        var result = restTemplate.getForObject(requestURL, Operation.class,
                operation.getArgFirst(), operation.getArgSecond());

        System.out.println("result: " + result);
    }
}
