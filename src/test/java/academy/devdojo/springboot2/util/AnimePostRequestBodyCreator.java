package academy.devdojo.springboot2.util;

import academy.devdojo.springboot2.requests.AnimePostRequestBody;

public class AnimePostRequestBodyCreator {

    public static AnimePostRequestBody createAnimePostRequestBody() {
        return new AnimePostRequestBody(AnimeCreator.createAnimeToBeSaved().getName(), null);
    }

}
