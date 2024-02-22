package ca.ulaval.glo4002.game.domain.movie;

import ca.ulaval.glo4002.game.domain.movie.factory.BoxOfficeFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BoxOfficeFactoryTest {

    private BoxOfficeFactory boxOfficeFactory;

    @BeforeEach
    public void setUp() {
        boxOfficeFactory = new BoxOfficeFactory();
    }

    @Test
    public void whenCreateNewBoxOffice_thenReturnNewBoxOffice() {

        BoxOffice newBoxOffice = boxOfficeFactory.createBoxOffice();

        assertNotNull(newBoxOffice);
    }
}
