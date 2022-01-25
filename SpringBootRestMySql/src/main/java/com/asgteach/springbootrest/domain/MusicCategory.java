// MusicCategory.java - MusicCategory Entity
package com.asgteach.springbootrest.domain;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "musiccategories")
public class MusicCategory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "musiccategoryid")
    private Integer musicCategoryId;
    @Column(name = "musiccategory")
    private String musicCategory;
    
    public MusicCategory() { }

    public Integer getMusicCategoryId() {
        return musicCategoryId;
    }

    public void setMusicCategoryId(Integer musicCategoryId) {
        this.musicCategoryId = musicCategoryId;
    }

    public String getMusicCategory() {
        return musicCategory;
    }

    public void setMusicCategory(String musicCategory) {
        this.musicCategory = musicCategory;
    }
    
    @Override
    public String toString() {
        return musicCategoryId + ": " + musicCategory;
    }   
}
