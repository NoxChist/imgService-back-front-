package org.testtask.demo.application.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
@Setter
@Embeddable
public class ImageId implements Serializable {
    private String author;
    private String imgName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageId imageId = (ImageId) o;
        return Objects.equals(author, imageId.author) &&
                Objects.equals(imgName, imageId.imgName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, imgName);
    }

}
