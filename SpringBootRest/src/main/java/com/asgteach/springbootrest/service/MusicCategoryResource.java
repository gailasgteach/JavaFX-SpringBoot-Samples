// MusicCategoryResource.java - Music Category REST resource
package com.asgteach.springbootrest.service;
import com.asgteach.springbootrest.domain.MusicCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicCategoryResource {

    @Autowired
    private MusicRepository musicRepository;

    @GetMapping("/musiccategories")
    public List<MusicCategory> retrieveAllMusicCategories() {
        return musicRepository.findAll();
    }

    @GetMapping("/musiccategories/{id}")
    public MusicCategory retrieveMusicCategory(@PathVariable Integer id) {
        Optional<MusicCategory> musicCategory = musicRepository.findById(id);
        if (!musicCategory.isPresent()) {
            throw new MusicCategoryNotFoundException("id: " + id);
        }
        return musicCategory.get();
    }

    @DeleteMapping("/musiccategories/{id}")
    public void deleteMusicCategory(@PathVariable Integer id) {
        musicRepository.deleteById(id);
    }

    @PostMapping("/musiccategories")
    public ResponseEntity<Object> createMusicCategory(
            @RequestBody MusicCategory musicCategory) {
        MusicCategory savedMusicCategory =
            musicRepository.save(musicCategory);
        System.out.println("Created: " + savedMusicCategory);
        return new ResponseEntity<>(savedMusicCategory, HttpStatus.CREATED);
    }

    @PutMapping("/musiccategories/{id}")
    public ResponseEntity<Object> updateMusicCategory(
            @RequestBody MusicCategory musicCategory, 
                @PathVariable Integer id) {
        Optional<MusicCategory> musicCategoryOptional =
            musicRepository.findById(id);

        if (!musicCategoryOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        musicCategory.setMusicCategoryId(id);
        MusicCategory updatedMusicCategory = 
            musicRepository.save(musicCategory);
        System.out.println("Updated: " + updatedMusicCategory);
        return new ResponseEntity<>(updatedMusicCategory, HttpStatus.OK);
    }
}
