package ca.ulaval.glo4002.game.domain.movie;

import ca.ulaval.glo4002.game.domain.character.Actor;
import ca.ulaval.glo4002.game.domain.character.CharacterID;
import ca.ulaval.glo4002.game.domain.money.Money;
import ca.ulaval.glo4002.game.domain.movie.casting.CastingStrategy;
import ca.ulaval.glo4002.game.domain.movie.state.MovieState;
import ca.ulaval.glo4002.game.domain.movie.state.NewlyAddedMovieState;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Movie {

    private static final int REQUIRED_CAST = 2;
    private static final double ACTORS_CUT = 0.05, AGENTS_CUT = 0.02;
    private final String title;
    private final Set<Actor> casting = new HashSet<>();
    private final Set<Actor> toBePaidActors = new HashSet<>();
    private final BoxOffice boxOffice;
    private final CastingStrategy castingStrategy;
    private final MovieType type;
    private final MovieID movieID;
    private MovieState movieState;
    private Set<Actor> potentialCasting = new HashSet<>();

    public Movie(MovieID movieID, String title, MovieType type, BoxOffice boxOffice, CastingStrategy castingStrategy) {
        this.movieID = movieID;
        this.title = title;
        this.type = type;
        this.boxOffice = boxOffice;
        this.castingStrategy = castingStrategy;
        this.movieState = new NewlyAddedMovieState(this);
    }

    public void changeState(MovieState movieState) {
        this.movieState = movieState;
    }

    public String getTitle() {
        return title;
    }

    public Set<Actor> getCasting() {
        return casting;
    }

    public Set<Actor> getPotentialCasting() {
        return potentialCasting.stream().filter(Actor::isAvailable).collect(Collectors.toSet());
    }

    public Money getBoxOfficeValue() {
        return boxOffice.getBoxOfficeGains();
    }

    public void addActorsToPotentialCast(Set<Actor> actors) {
        potentialCasting.addAll(actors);
    }

    public boolean isCastingComplete() {
        return casting.size() == REQUIRED_CAST;
    }

    public void freeActors() {
        casting.forEach(actor -> {
            actor.freeFromCurrentMovie();
            toBePaidActors.add(actor);
        });
        casting.clear();
    }

    public void auditionPotentialCast() {
        removeAllBusyActorsFromPotentialCast();
        choosePotentialCast();
    }

    private void choosePotentialCast() {
        casting.addAll(castingStrategy.selectMovieCast(this, REQUIRED_CAST));
        casting.forEach(actor -> actor.turnAMovie(this));
        potentialCasting.clear();
    }

    public void payActors() {
        for (Actor actor : getCasting()) {
            actor.gainSalary();
        }
    }

    public Money calculateBonusActors() {
        Money bonus = boxOffice.getBoxOfficeGains();
        return bonus.multiply(ACTORS_CUT);
    }

    public Money calculateBonusAgents() {
        Money bonus = boxOffice.getBoxOfficeGains();
        return bonus.multiply(AGENTS_CUT);
    }

    public void giveBonus() {
        for (Actor actor : toBePaidActors) {
            actor.gainMoney(calculateBonusActors());
            actor.sendBonusToAgent(calculateBonusAgents());
        }
    }

    public void calculateGainInBoxOffice() {
        boxOffice.calculateBoxOffice(toBePaidActors);
        boxOffice.boostBoxOffice(toBePaidActors);
    }

    public MovieStatus getStatus() {
        return movieState.getStatus();
    }

    private void removeAllBusyActorsFromPotentialCast() {
        potentialCasting = potentialCasting.stream().filter(Actor::isAvailable).collect(Collectors.toSet());
    }

    public void removeFromCasting(CharacterID actorID) {
        potentialCasting.removeIf(actor -> actor.getCharacterID().equals(actorID));
        casting.removeIf(actor -> actor.getCharacterID().equals(actorID));
    }

    public String getDisplayType() {
        return type.name();
    }

    public void changeStep() {
        movieState.changeStep();
    }

    public MovieID getMovieID() {
        return movieID;
    }
}
