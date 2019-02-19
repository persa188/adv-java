package reflection;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Optional;
import java.util.UUID;

@Builder
@Data
/**
 * a class to use reflection on
 */
public class Person {
    @NonNull public final Integer age;
    @NonNull public final UUID guid;
    @NonNull public final String name;
    @NonNull public final String sex;
     public Optional<Boolean> inTeam;

    public boolean isInTeam() {
        return inTeam.orElse(false);
    }

    public void setInTeam(boolean flag) {
        this.inTeam = Optional.of(flag);
    }
}
