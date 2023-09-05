package dat.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.regex.Pattern;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
public class Phone
{
    // TODO - find number is a String it's very easy to check with regex
    //  if a number is equal to danish telefon number. Maybe change back to Integer :/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_id")
    private Integer id;

    private String number;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private PhoneType type;

    @ManyToOne(fetch = FetchType.EAGER)
    private Person person;

    public Phone(String number, PhoneType type)
    {
        this.number = number;
        this.type = type;
    }

    @PrePersist
    private void beforeSave()
    {
        if (!phoneNumberValidation(number))
        {
            throw new IllegalArgumentException();
        }
    }

    @PreUpdate
    private void beforeUpdate()
    {
        if (!phoneNumberValidation(number))
        {
            throw new IllegalArgumentException();
        }
    }

    public boolean phoneNumberValidation(String number)
    {
        String regex = "(?:(?:\\+45|0045|45)[\\s-]?)?\\d{2}[\\s-]?\\d{2}[\\s-]?\\d{2}[\\s-]?\\d{2}";

        return Pattern.matches(regex, number);
    }
}