package dat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
public class Phone
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_id")
    private Integer id;

    private Integer number;

    private Type type;

    @ManyToOne
    private PersonDetail personDetail;

    public Phone(Integer number, Type type)
    {
        this.number = number;
        this.type = type;
    }

}
