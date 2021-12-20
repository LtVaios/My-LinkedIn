package uoa.di.tedbackend.audio_files;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.user_impl.User;

import javax.persistence.*;

@Data
@Entity
public class Audio {
    public Audio() {
        super();
    }

    public Audio(String name, String type, byte[] audByte) {
        this.name = name;
        this.type = type;
        this.audByte = audByte;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Lob //large object
    @Column(name = "audByte")
    private byte[] audByte;

}
