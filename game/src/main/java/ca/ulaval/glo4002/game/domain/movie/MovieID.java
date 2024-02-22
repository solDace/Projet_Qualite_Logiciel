package ca.ulaval.glo4002.game.domain.movie;

public class MovieID {
    private final String movieName;

    public MovieID(String movieName) {
        this.movieName = movieName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MovieID otherMovieID)) {
            return false;
        }

        return otherMovieID.movieName.equals(movieName);
    }

    @Override
    public int hashCode() {
        return movieName.hashCode();
    }
}
