package gifengine.mapper;


import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

/*
*Mapper defaults
* */
@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface DefaultMapperConfig {
}
