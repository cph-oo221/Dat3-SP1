package dat.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Hobby
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer h_id;
    private String link;
    private String category;
    private String type;
    @OneToMany(mappedBy = "hobby", cascade = CascadeType.ALL)
    private Set<Interests> interests = new HashSet<>();

    public Hobby(String link, String category, String type)
    {
        this.link = link;
        this.category = category;
        this.type = type;
    }
}