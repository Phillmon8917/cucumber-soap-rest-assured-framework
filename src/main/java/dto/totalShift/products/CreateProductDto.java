package dto.totalShift.products;

public record CreateProductDto(
        String name,
        String price,
        String description,
        String stock,
        String category
) {
}
