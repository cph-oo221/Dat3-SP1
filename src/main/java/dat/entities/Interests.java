package dat.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Interests
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer i_id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Person person;
    @ManyToOne(fetch = FetchType.EAGER)
    Hobby hobby;
}
