// MusicCategory.java - MusicCategory domain class
package com.asgteach.springboot.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MusicCategory {
    private Integer musicCategoryId;
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
