package com.monsoko.training.springboot.comicbookslibrary.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "album")
@Data
public class Album {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    
    @NotEmpty (message = "Ttile must not be empty")
    public String title;
    
    public int number;
    public LocalDate publicationDate;
    public String coverName;
    
 
}
