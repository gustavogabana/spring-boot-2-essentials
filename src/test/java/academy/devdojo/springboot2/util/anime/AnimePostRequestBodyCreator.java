package academy.devdojo.springboot2.util.anime;

import academy.devdojo.springboot2.requests.animeRequests.AnimePostRequestBody;

public class AnimePostRequestBodyCreator {

    public static AnimePostRequestBody createAnimePostRequestBody() {
        return new AnimePostRequestBody(AnimeCreator.createAnimeToBeSaved().getName(), null);
    }

}
