package com.target.VolunteeringPlatform.model;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Images {

    @Id
    @GeneratedValue(generator = "image_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "image_id_seq", sequenceName = "image_id_seq", allocationSize = 1)
    @Column(name="image_id")
    private int image_id;

    @Column(name="image")
    private String image;

    public Images(String image) {
        this.image = image;
    }

    public Images() {
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Images(int image_id, String image) {
        this.image_id = image_id;
        this.image = image;
    }
}
