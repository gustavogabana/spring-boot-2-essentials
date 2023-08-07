package academy.devdojo.springboot2.util;

import academy.devdojo.springboot2.requests.AnimePutRequestBody;

public class AnimePutRequestBodyCreator {

    public static AnimePutRequestBody createAnimePostRequestBody() {
        return new AnimePutRequestBody(AnimeCreator.createValidUpdatedAnime().getId(), AnimeCreator.createValidUpdatedAnime().getName());
    }

}
