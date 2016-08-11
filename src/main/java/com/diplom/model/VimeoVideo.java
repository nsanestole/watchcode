package com.diplom.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Stole on 5/6/2016.
 */
@Entity
@Table(name = "vimeovideo")
public class VimeoVideo implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;



    @Size(min = 1, max = 45)
    @Column(name = "video_uri")
    private String videoUri;
    @Size(max = 100)
    @Column(name = "name")
    private String name;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Size(max = 45)
    @Column(name = "source")
    private String source;

    public VimeoVideo() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Size(max = 45)
    @Column(name = "description")
    private String description;

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    @Size(max = 200)
    @Column(name = "image_uri")
    private String imageUri;

    public User getUser_id() {
        return user;
    }

    public void setUser_id(User user_id) {
        this.user = user_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;
}
