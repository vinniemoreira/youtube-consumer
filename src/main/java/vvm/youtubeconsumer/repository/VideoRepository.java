package vvm.youtubeconsumer.repository;

import vvm.youtubeconsumer.models.Video;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "video", path = "video")
public interface VideoRepository extends PagingAndSortingRepository<Video, Long> {

    Video findById(@Param("id") long id);

}
