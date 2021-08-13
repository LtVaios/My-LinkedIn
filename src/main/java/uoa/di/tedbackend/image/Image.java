package uoa.di.tedbackend.image;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Image {
    public Image() {
        super();
    }

    public Image(String name, String type, byte[] picByte) {
        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    //image bytes can have large lengths so we specify a value

    //which is more than the default length for picByte column
    @Column(name = "picByte", length = 1000)
    private byte[] picByte;
}
