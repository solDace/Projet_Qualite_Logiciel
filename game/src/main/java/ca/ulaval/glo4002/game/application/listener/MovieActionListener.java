package ca.ulaval.glo4002.game.application.listener;

import ca.ulaval.glo4002.game.application.GameActionListener;
import ca.ulaval.glo4002.game.application.MovieService;
import ca.ulaval.glo4002.game.domain.action.AddMovieGameAction;
import ca.ulaval.glo4002.game.domain.action.GameAction;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;

@Service
public class MovieActionListener implements GameActionListener {
    private final MovieService movieService;

    @Inject
    public MovieActionListener(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public void onGameAction(GameAction gameAction) {
        switch (gameAction.getActionType()) {
            case ADD_MOVIE -> {
                AddMovieGameAction convertedGameAction = (AddMovieGameAction) gameAction;
                movieService.addMovie(convertedGameAction.title(), convertedGameAction.type());
            }
            case ALL_MOVIES_CHANGE_STEP -> {
                movieService.allMoviesChangeStep();
            }
        }
    }
}
