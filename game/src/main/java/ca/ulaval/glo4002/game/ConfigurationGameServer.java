package ca.ulaval.glo4002.game;

import ca.ulaval.glo4002.game.application.*;
import ca.ulaval.glo4002.game.application.listener.CharacterActionListener;
import ca.ulaval.glo4002.game.application.listener.CharacterInteractionActionListener;
import ca.ulaval.glo4002.game.application.listener.EliminationActionListener;
import ca.ulaval.glo4002.game.application.listener.HamstagramActionListener;
import ca.ulaval.glo4002.game.application.listener.LawsuitActionListener;
import ca.ulaval.glo4002.game.application.listener.MovieActionListener;
import ca.ulaval.glo4002.game.application.listener.RattedInActionListener;
import ca.ulaval.glo4002.game.application.listener.RepresentationActionListener;
import ca.ulaval.glo4002.game.application.listener.ResetGameActionListener;
import ca.ulaval.glo4002.game.domain.action.CharacterInteractionGameActionFactory;
import ca.ulaval.glo4002.game.domain.character.CharacterRepository;
import ca.ulaval.glo4002.game.domain.character.Hamstrology;
import ca.ulaval.glo4002.game.domain.character.LawyerSelector;
import ca.ulaval.glo4002.game.domain.character.factory.CharacterFactory;
import ca.ulaval.glo4002.game.domain.character.factory.CharacterIDFactory;
import ca.ulaval.glo4002.game.domain.contact.factory.RattedInContactFactory;
import ca.ulaval.glo4002.game.domain.game.GameRepository;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccountFactory;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramIDFactory;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramRepository;
import ca.ulaval.glo4002.game.domain.lawsuit.LawsuitFactory;
import ca.ulaval.glo4002.game.domain.lawsuit.LawsuitRepository;
import ca.ulaval.glo4002.game.domain.movie.MovieRepository;
import ca.ulaval.glo4002.game.domain.movie.casting.CastingStrategyFactory;
import ca.ulaval.glo4002.game.domain.movie.factory.BoxOfficeFactory;
import ca.ulaval.glo4002.game.domain.movie.factory.MovieFactory;
import ca.ulaval.glo4002.game.domain.movie.factory.MovieIDFactory;
import ca.ulaval.glo4002.game.domain.rattedin.RattedInRepository;
import ca.ulaval.glo4002.game.domain.rattedin.factory.RattedInAccountFactory;
import ca.ulaval.glo4002.game.domain.rattedin.factory.RattedInIDFactory;
import ca.ulaval.glo4002.game.infrastructure.persistence.memory.CharacterRepositoryInMemory;
import ca.ulaval.glo4002.game.infrastructure.persistence.memory.GameRepositoryInMemory;
import ca.ulaval.glo4002.game.infrastructure.persistence.memory.HamstagramRepositoryInMemory;
import ca.ulaval.glo4002.game.infrastructure.persistence.memory.LawsuitRepositoryInMemory;
import ca.ulaval.glo4002.game.infrastructure.persistence.memory.MovieRepositoryInMemory;
import ca.ulaval.glo4002.game.infrastructure.persistence.memory.RattedInRepositoryInMemory;
import ca.ulaval.glo4002.game.interfaces.rest.dto.converter.CharacterActionConverter;
import ca.ulaval.glo4002.game.interfaces.rest.dto.lawsuit.LawsuitDtoAssembler;
import ca.ulaval.glo4002.game.interfaces.rest.dto.movie.MovieDtoAssembler;
import ca.ulaval.glo4002.game.interfaces.rest.dto.movie.MoviesDtoAssembler;
import ca.ulaval.glo4002.game.interfaces.rest.dto.validator.CharacterActionCreationDtoValidator;
import ca.ulaval.glo4002.game.interfaces.rest.dto.validator.CharacterCreationDtoValidator;
import ca.ulaval.glo4002.game.interfaces.rest.mapper.NotFoundExceptionMapper;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ConfigurationGameServer extends AbstractBinder {
    @Override
    protected void configure() {
        // Factories
        bindAsContract(HamstagramAccountFactory.class);
        bindAsContract(HamstagramIDFactory.class);
        bindAsContract(CharacterFactory.class);
        bindAsContract(RattedInIDFactory.class);
        bindAsContract(RattedInContactFactory.class);
        bindAsContract(RattedInAccountFactory.class);
        bindAsContract(MovieFactory.class);
        bindAsContract(MovieIDFactory.class);
        bindAsContract(BoxOfficeFactory.class);
        bindAsContract(CastingStrategyFactory.class);
        bindAsContract(LawsuitFactory.class);
        bindAsContract(CharacterInteractionGameActionFactory.class);
        bindAsContract(CharacterIDFactory.class);

        // Services
        bindAsContract(LawsuitService.class);
        bindAsContract(RattedInService.class);
        bindAsContract(HamstagramService.class);
        bindAsContract(CharacterService.class);
        bindAsContract(RepresentationService.class);
        bindAsContract(EliminationService.class);
        bindAsContract(MovieService.class);
        bindAsContract(CharacterInteractionService.class);
        bindAsContract(ResetGameService.class);
        bindAsContract(GameService.class);

        // Repositories
        bind(HamstagramRepositoryInMemory.class).to(HamstagramRepository.class);
        bind(GameRepositoryInMemory.class).to(GameRepository.class);
        bind(CharacterRepositoryInMemory.class).to(CharacterRepository.class);
        bind(RattedInRepositoryInMemory.class).to(RattedInRepository.class);
        bind(MovieRepositoryInMemory.class).to(MovieRepository.class);
        bind(LawsuitRepositoryInMemory.class).to(LawsuitRepository.class);

        // Listeners and Emitters
        bindAsContract(GameActionEmitter.class);

        bindAsContract(RepresentationActionListener.class);
        bindAsContract(CharacterActionListener.class);
        bindAsContract(HamstagramActionListener.class);
        bindAsContract(EliminationActionListener.class);
        bindAsContract(ResetGameActionListener.class);
        bindAsContract(RattedInActionListener.class);
        bindAsContract(MovieActionListener.class);
        bindAsContract(CharacterInteractionActionListener.class);
        bindAsContract(LawsuitActionListener.class);

        // Validators
        bindAsContract(CharacterCreationDtoValidator.class);
        bindAsContract(CharacterActionCreationDtoValidator.class);

        // Assemblers
        bindAsContract(MovieDtoAssembler.class);
        bindAsContract(MoviesDtoAssembler.class);
        bindAsContract(LawsuitDtoAssembler.class);

        // Mappers
        bindAsContract(NotFoundExceptionMapper.class);

        bindAsContract(Hamstrology.class);
        bindAsContract(CharacterActionConverter.class);
        bindAsContract(LawyerSelector.class);
    }
}
