package dat.dto;

import lombok.Getter;

@Getter
public class AdressIdStreetNumberDTO
{
    private int id;
    private String street;
    private String number;

    public AdressIdStreetNumberDTO(int id, String street, String number)
    {
        this.id = id;
        this.street = street;
        this.number = number;
    }
}


