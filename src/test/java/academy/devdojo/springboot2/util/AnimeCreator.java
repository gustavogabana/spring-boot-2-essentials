package academy.devdojo.springboot2.util;

import academy.devdojo.springboot2.domain.Anime;

public class AnimeCreator {
    public static Anime createAnimeToBeSaved() {
        return new Anime(null, "Berserk");
    }
    public static Anime createValidAnime() {
        return new Anime(1L, "Berserk");
    }
    public static Anime createValidUpdatedAnime() {
        return new Anime(1L, "Hellsing");
    }
}
