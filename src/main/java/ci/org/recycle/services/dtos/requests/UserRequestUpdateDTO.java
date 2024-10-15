package ci.org.recycle.services.dtos.requests;

public record UserRequestUpdateDTO (
        String password,
        Boolean enabled,
        Boolean accountNonLocked
) {
}
