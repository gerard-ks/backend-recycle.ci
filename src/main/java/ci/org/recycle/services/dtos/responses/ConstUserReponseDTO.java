package ci.org.recycle.services.dtos.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ConstUserReponseDTO {
    public static UUID userId;
    public static String email;
}
