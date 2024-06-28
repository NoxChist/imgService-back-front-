package org.testtask.demo.application.entity;

import jakarta.persistence.*;
import lombok.*;
import org.testtask.demo.security.auth.user.entitiy.ImageServiceUser;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(schema = "content", name = "image")

public class ImageEntity {
    @EmbeddedId
    private ImageId id;

    @Column(nullable = false)
    private LocalDate publicationDate;

    @MapsId("author")
    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "email", nullable = false)
    private ImageServiceUser authorEntity;
}
