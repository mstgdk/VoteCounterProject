package com.votecounter.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_imagefile")
public class ImageFile {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid")
    private String id;//tahmin edilememesi için string
    private String name;
    private String type;
    private long length;

    @OneToOne(cascade = CascadeType.ALL)
    private ImageData imageData;
    //Requirement larda --arabanın fotoğrafını istediği için ImageData üzerinden ImageFile'a ulaşabiliyoruz. fotoğrafların ait olduğu arabayı istememiş
    public ImageFile(String name,String type,ImageData imageData){
        this.name=name;
        this.type=type;
        this.imageData=imageData;
        this.length=imageData.getData().length;
    }

}
