package utils.faker;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class FakerUtil {

    private final Faker faker = Faker.instance();
    private final List<String> roles = Arrays.asList("admin", "user", "guest", "moderator", "editor", "viewer", "manager");
    private final List<String> orderStatuses = Arrays.asList("pending", "shipped", "delivered", "cancelled");

    public String getRandomName() {
        return faker.name().firstName();
    }

    public String getRandomEmail() {
        return faker.internet().emailAddress();
    }

    public String getRandomRole() {
        return roles.get(faker.random().nextInt(roles.size()));
    }

    public String getDifferentRole(String currentRole) {
        String newRole;

        do {
            newRole = roles.get(faker.random().nextInt(roles.size()));
        } while (newRole.equals(currentRole));

        return newRole;
    }

    public String getRandomAge(){
        return String.valueOf(faker.number().numberBetween(18, 90));
    }

    public int getRandomNumber(int min, int max){
        return faker.number().numberBetween(min, max);
    }

    public String getRandomUUID() {
        return faker.internet().uuid();
    }

    public String getRandomProductName() {
        return faker.commerce().productName();
    }

    public String getRandomPrice() {
        return String.valueOf(faker.number().numberBetween(10, 1000));
    }

    public String getRandomDescription() {
        return faker.lorem().sentence();
    }

    public String getRandomStock() {
        return String.valueOf(faker.number().numberBetween(1, 100));
    }

    public String getRandomCategory() {
        return faker.commerce().department();
    }

    public String getRandomStatus() {
        return orderStatuses.get(faker.random().nextInt(orderStatuses.size()));
    }

    public String getDifferentStatus(String currentStatus) {
        String newStatus;

        do {
            newStatus = orderStatuses.get(faker.random().nextInt(orderStatuses.size()));
        } while (newStatus.equals(currentStatus));

        return newStatus;
    }

    public String getRandomQuantity() {
        return String.valueOf(faker.number().numberBetween(1, 10));
    }

    public String getRandomNotes() {
        return faker.lorem().sentence();
    }
}
