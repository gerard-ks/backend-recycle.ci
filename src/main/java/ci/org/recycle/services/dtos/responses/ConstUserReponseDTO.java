package ci.org.recycle.services.dtos.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConstUserReponseDTO {
    public static Long userId;
    public static String email;
    public static Long collectionPointId;
}
