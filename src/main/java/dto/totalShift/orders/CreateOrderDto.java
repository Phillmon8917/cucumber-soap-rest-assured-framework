package dto.totalShift.orders;

public record CreateOrderDto(
        String userId,
        String productId,
        String quantity,
        String status,
        String notes
) {
}
