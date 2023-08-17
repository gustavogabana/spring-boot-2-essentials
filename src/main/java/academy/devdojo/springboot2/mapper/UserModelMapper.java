package academy.devdojo.springboot2.mapper;

import academy.devdojo.springboot2.domain.UserModel;
import academy.devdojo.springboot2.requests.userModelRequests.UserModelPostRequestBody;
import academy.devdojo.springboot2.requests.userModelRequests.UserModelPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class UserModelMapper {
    public static final UserModelMapper INSTANCE = Mappers.getMapper(UserModelMapper.class);
    public abstract UserModel toUserModel(UserModelPostRequestBody userModelPostRequestBody);
    public abstract UserModel toUserModel(UserModelPutRequestBody userModelPutRequestBody);
}
