package dat.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Hobby
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer h_id;
    private String name;
    private String wikilink;
    private String category;
    private String type;
    @OneToMany(mappedBy = "hobby", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Interest> interests = new HashSet<>();

    public Hobby(String wikilink, String category, String type)
    {
        this.wikilink = wikilink;
        this.category = category;
        this.type = type;
    }
}