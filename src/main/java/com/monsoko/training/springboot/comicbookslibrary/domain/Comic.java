package com.monsoko.training.springboot.comicbookslibrary.domain;


import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "comic")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comic {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    
    @NotEmpty (message = "Script Writer must not be empty")
    public String title;
   
    @NotEmpty (message = "Script Writer must not be empty")
    public String scriptWriter;
  
    @NotEmpty (message = "Illustrator Writer must not be empty")
    public String illustrator;
    
    @NotEmpty (message = "Publisher Writer must not be empty")
    public String publisher;

    public boolean favorite;

    @Builder.Default
    @Setter(value = AccessLevel.PRIVATE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "COMIC_ID")
    private Set<Album> albums = new HashSet<>();

    public Comic addAlbums(Album... albums) {
        Assert.notNull(albums, "albums must be not null!");
        Arrays.stream(albums).forEach(this.albums::add);
        return this;
    }

    public Comic removeAlbums(Album... albums) {
        Assert.notNull(albums, "albums must be not null!");
        Arrays.stream(albums).forEach(this.albums::remove);
        return this;
	}

}
