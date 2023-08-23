package academy.devdojo.springboot2.util.anime;

import academy.devdojo.springboot2.requests.animeRequests.AnimePutRequestBody;

public class AnimePutRequestBodyCreator {

    public static AnimePutRequestBody createAnimePostRequestBody() {
        return new AnimePutRequestBody(AnimeCreator.createValidUpdatedAnime().getId(), AnimeCreator.createValidUpdatedAnime().getName());
    }

}
