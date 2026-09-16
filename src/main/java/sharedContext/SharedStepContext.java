package sharedContext;

import dto.totalShift.orders.CreateOrderDto;
import dto.totalShift.products.CreateProductDto;
import dto.totalShift.users.CreateUserDto;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SharedStepContext {

    private Response response;
    private CreateUserDto createUserDto;
    private CreateProductDto createProductDto;
    private CreateOrderDto createOrderDto;
    private String createdUserId;
    private String createdProductId;

    public void reset() {
        this.response = null;
        this.createUserDto = null;
        this.createProductDto = null;
        this.createOrderDto = null;
        this.createdUserId = null;
        this.createdProductId = null;
    }
}
