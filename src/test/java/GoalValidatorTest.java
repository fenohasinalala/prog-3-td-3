import app.foot.controller.validator.GoalValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//TODO-1: complete these tests
public class GoalValidatorTest {
    GoalValidator subject = new GoalValidator();

    public static void assertThrowsRuntimeException(String MessageError, Executable executable) {
        RuntimeException Exception = assertThrows(RuntimeException.class, executable);
        assertEquals(MessageError, Exception.getMessage());
    }

    @Test
    void accept_ok() {
        app.foot.controller.rest.PlayerScorer scorer = rakotoModelScorer();
        assertDoesNotThrow(() -> subject.accept(scorer));
    }

    //Mandatory attributes not provided : scoreTime
    @Test
    void accept_ko() {
        app.foot.controller.rest.PlayerScorer scorer = bemaModelScorer();
        assertThrows(NullPointerException.class, () -> subject.accept(scorer));

    }

    @Test
    void when_guardian_throws_exception() {
        app.foot.controller.rest.PlayerScorer scorer = casillasModelScorer();
        assertThrowsRuntimeException("Player#"+scorer.getPlayer().getId()+" is a guardian so they cannot score.",() -> subject.accept(scorer));
    }

    @Test
    void when_score_time_greater_than_90_throws_exception() {
        app.foot.controller.rest.PlayerScorer scorer = ronaldoModelScorer();
        assertThrowsRuntimeException("Player#"+scorer.getPlayer().getName()+" cannot score before after minute 90.",() -> subject.accept(scorer));
    }

    @Test
    void when_score_time_less_than_0_throws_exception() {
        app.foot.controller.rest.PlayerScorer scorer = messiModelScorer();
        assertThrowsRuntimeException("Player#"+scorer.getPlayer().getId()+" cannot score before before minute 0.",() -> subject.accept(scorer));
    }

    private static app.foot.controller.rest.PlayerScorer rakotoModelScorer() {
        return app.foot.controller.rest.PlayerScorer.builder()
                .player(playerModelRakoto())
                .isOG(false)
                .scoreTime(10)
                .build();
    }
    private static app.foot.controller.rest.Player playerModelRakoto() {
        return app.foot.controller.rest.Player.builder()
                .id(1)
                .name("Rakoto")
                .isGuardian(false)
                .build();
    }


    private static app.foot.controller.rest.PlayerScorer bemaModelScorer() {
        return app.foot.controller.rest.PlayerScorer.builder()
                .player(playerModelBema())
                .isOG(false)
                .scoreTime(null)
                .build();
    }
    private static app.foot.controller.rest.Player playerModelBema() {
        return app.foot.controller.rest.Player.builder()
                .id(1)
                .name("Bema")
                .isGuardian(false)
                .build();
    }


    private static app.foot.controller.rest.PlayerScorer casillasModelScorer() {
        return app.foot.controller.rest.PlayerScorer.builder()
                .player(playerModelCasillas())
                .isOG(false)
                .scoreTime(10)
                .build();
    }
    private static app.foot.controller.rest.Player playerModelCasillas() {
        return app.foot.controller.rest.Player.builder()
                .id(99)
                .name("Casillas")
                .isGuardian(true)
                .build();
    }


    private static app.foot.controller.rest.PlayerScorer messiModelScorer() {
        return app.foot.controller.rest.PlayerScorer.builder()
                .player(playerModelMessi())
                .isOG(false)
                .scoreTime(-1)
                .build();
    }
    private static app.foot.controller.rest.Player playerModelMessi() {
        return app.foot.controller.rest.Player.builder()
                .id(1)
                .name("Messi")
                .isGuardian(false)
                .build();
    }


    private static app.foot.controller.rest.PlayerScorer ronaldoModelScorer() {
        return app.foot.controller.rest.PlayerScorer.builder()
                .player(playerModelRonaldo())
                .isOG(false)
                .scoreTime(91)
                .build();
    }
    private static app.foot.controller.rest.Player playerModelRonaldo() {
        return app.foot.controller.rest.Player.builder()
                .id(1)
                .name("Ronaldo")
                .isGuardian(false)
                .build();
    }

}
