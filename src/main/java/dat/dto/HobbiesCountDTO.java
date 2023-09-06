package dat.dto;

import dat.entities.Hobby;
import lombok.Getter;

@Getter
public class HobbiesCountDTO
{
    private Hobby hobby;
    private Long count;

    public HobbiesCountDTO(Hobby hobby, Long count)
    {
        this.hobby = hobby;
        this.count = count;
    }
}