package mata.hoshi.app.mapper;

import mata.hoshi.app.dto.projection.request.ProjectionRequest;
import mata.hoshi.app.dto.projection.response.ProjectionResponse;
import mata.hoshi.app.model.Projection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProjectionMapper {


    ProjectionResponse toResponse(Projection projection);


    @Mapping(target = "id", ignore = true)
    Projection toModel(ProjectionRequest request);

}