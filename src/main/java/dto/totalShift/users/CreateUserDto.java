package dto.totalShift.users;

public record CreateUserDto(
        String name,
        String email,
        String role,
        String age
) {
}
