package projekti.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Account extends AbstractPersistable<Long> {
    
    @NotEmpty(message="Nimi ei voi olla tyhjä")
    @Size(min=5, max=30, message="Nimen merkkimäärän tulee olla välillä {min} ja {max}")
    private String name;
    
    @NotEmpty(message="Käyttäjänimi ei voi olla tyhjä")
    @Size(min=3, max=30, message="Käyttäjänimen merkkimäärän tulee olla välillä {min} ja {max}")
    private String username;
    
    @NotEmpty(message="Lempinimi ei voi olla tyhjä")
    @Size(min=3, max=20, message="Lempinimen merkkimäärän tulee olla välillä {min} ja {max}")
    private String handle;
    
    @NotEmpty(message="Salasana ei voi olla tyhjä")
    @Size(min=5, message="Salasanassa tulee olla vähintään {min} merkkiä")
    private String password;
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Account> connections;
    
}
