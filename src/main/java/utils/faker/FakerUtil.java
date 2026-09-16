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

    /**
     * Returns a random first name.
     */
    public String getRandomName() {
        return faker.name().firstName();
    }

    /**
     * Returns a random, well-formed email address.
     */
    public String getRandomEmail() {
        return faker.internet().emailAddress();
    }

    /**
     * Returns a random role from the supported set.
     */
    public String getRandomRole() {
        return roles.get(faker.random().nextInt(roles.size()));
    }

    /**
     * Returns a random role guaranteed to differ from the given current role.
     */
    public String getDifferentRole(String currentRole) {
        String newRole;

        do {
            newRole = roles.get(faker.random().nextInt(roles.size()));
        } while (newRole.equals(currentRole));

        return newRole;
    }

    /**
     * Returns a random age between 18 and 90.
     */
    public String getRandomAge(){
        return String.valueOf(faker.number().numberBetween(18, 90));
    }

    /**
     * Returns a random number within the given inclusive-exclusive range.
     */
    public int getRandomNumber(int min, int max){
        return faker.number().numberBetween(min, max);
    }

    /**
     * Returns a random UUID.
     */
    public String getRandomUUID() {
        return faker.internet().uuid();
    }

    /**
     * Returns a random product name.
     */
    public String getRandomProductName() {
        return faker.commerce().productName();
    }

    /**
     * Returns a random price between 10 and 1000.
     */
    public String getRandomPrice() {
        return String.valueOf(faker.number().numberBetween(10, 1000));
    }

    /**
     * Returns a random sentence suitable for a product description.
     */
    public String getRandomDescription() {
        return faker.lorem().sentence();
    }

    /**
     * Returns a random stock quantity between 1 and 100.
     */
    public String getRandomStock() {
        return String.valueOf(faker.number().numberBetween(1, 100));
    }

    /**
     * Returns a random product category.
     */
    public String getRandomCategory() {
        return faker.commerce().department();
    }

    /**
     * Returns a random order status from the supported set.
     */
    public String getRandomStatus() {
        return orderStatuses.get(faker.random().nextInt(orderStatuses.size()));
    }

    /**
     * Returns a random order status guaranteed to differ from the given current status.
     */
    public String getDifferentStatus(String currentStatus) {
        String newStatus;

        do {
            newStatus = orderStatuses.get(faker.random().nextInt(orderStatuses.size()));
        } while (newStatus.equals(currentStatus));

        return newStatus;
    }

    /**
     * Returns a random order quantity between 1 and 10.
     */
    public String getRandomQuantity() {
        return String.valueOf(faker.number().numberBetween(1, 10));
    }

    /**
     * Returns a random sentence suitable for order notes.
     */
    public String getRandomNotes() {
        return faker.lorem().sentence();
    }
}
