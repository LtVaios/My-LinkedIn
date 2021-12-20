package uoa.di.tedbackend.image;

import java.util.List;

public interface ImageRepositoryCustom {
    List<Image> findPostImages(int post_id);
}
