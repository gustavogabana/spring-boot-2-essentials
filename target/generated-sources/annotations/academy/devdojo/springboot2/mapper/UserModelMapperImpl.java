package academy.devdojo.springboot2.mapper;

import academy.devdojo.springboot2.domain.UserModel;
import academy.devdojo.springboot2.requests.userModelRequests.UserModelPostRequestBody;
import academy.devdojo.springboot2.requests.userModelRequests.UserModelPutRequestBody;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-27T17:18:17-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class UserModelMapperImpl extends UserModelMapper {

    @Override
    public UserModel toUserModel(UserModelPostRequestBody userModelPostRequestBody) {
        if ( userModelPostRequestBody == null ) {
            return null;
        }

        UserModel.UserModelBuilder userModel = UserModel.builder();

        userModel.name( userModelPostRequestBody.getName() );
        userModel.username( userModelPostRequestBody.getUsername() );
        userModel.password( userModelPostRequestBody.getPassword() );
        userModel.authorities( userModelPostRequestBody.getAuthorities() );

        return userModel.build();
    }

    @Override
    public UserModel toUserModel(UserModelPutRequestBody userModelPutRequestBody) {
        if ( userModelPutRequestBody == null ) {
            return null;
        }

        UserModel.UserModelBuilder userModel = UserModel.builder();

        userModel.id( userModelPutRequestBody.getId() );
        userModel.name( userModelPutRequestBody.getName() );
        userModel.username( userModelPutRequestBody.getUsername() );
        userModel.password( userModelPutRequestBody.getPassword() );
        userModel.authorities( userModelPutRequestBody.getAuthorities() );

        return userModel.build();
    }
}
