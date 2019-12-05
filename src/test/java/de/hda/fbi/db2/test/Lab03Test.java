package de.hda.fbi.db2.test;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.Type;
import org.eclipse.persistence.internal.jpa.metamodel.EntityTypeImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import de.hda.fbi.db2.api.Lab01Data;
import de.hda.fbi.db2.api.Lab03Game;
import de.hda.fbi.db2.controller.Controller;

/**
 * Created by l.koehler on 05.08.2019.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Lab03Test {

  private static Metamodel metaData;

  private static Controller controller;

  private static EntityType<?> gameEntity;

  private static synchronized void setGameEntity(EntityType<?> gameEntity) {
    Lab03Test.gameEntity = gameEntity;
  }

  @BeforeClass
  public static void init() {
    controller = Controller.getInstance();
    if (controller.getLab01Data() == null) {
      Assert.fail("Could not find Lab01Data Implementation");
      System.exit(-1);
    }
    if (controller.getLab02EntityManager() == null) {
      Assert.fail("Could not find Lab02EntityManager Implementation");
      System.exit(-1);
    }
    if (controller.getLab03Game() == null) {
      Assert.fail("Could not find Lab03Game Implementation");
      System.exit(-1);
    }

    if (!controller.isCsvRead()) {
      controller.readCsv();
    }

    if (!controller.isPersisted()) {
      controller.persistData();
    }

    try {
      if (controller.getLab02EntityManager().getEntityManager() == null) {
        Assert.fail("Lab02EntityManager.getEntityManager() returns null");
      }
      metaData = controller.getLab02EntityManager().getEntityManager().getMetamodel();
    } catch (Exception e) {
      Assert.fail("Exception during entityManager creation");
    }
  }

  @Test
  public void test1Functionality() {
    if (metaData == null) {
      Assert.fail("No MetaModel");
    }

    Lab03Game gameController = controller.getLab03Game();
    Lab01Data lab01Data = controller.getLab01Data();
    List<Object> questions = new ArrayList<>();
    questions.add(lab01Data.getQuestions().get(0));
    questions.add(lab01Data.getQuestions().get(1));
    Object game = gameController.createGame("PlayerName", questions);
    gameController.simulateGame(game);
    gameController.persistGame(game);
  }

  @Test
  public void test2FindGameEntity() {
    if (metaData == null) {
      Assert.fail("No MetaModel");
    }

    Lab03Game gameController = controller.getLab03Game();
    Lab01Data lab01Data = controller.getLab01Data();
    List<Object> questions = new ArrayList<>();
    questions.add(lab01Data.getQuestions().get(0));
    questions.add(lab01Data.getQuestions().get(1));
    Object game = gameController.createGame("PlayerName", questions);

    boolean gameFound = false;
    for (EntityType<?> clazzes : metaData.getEntities()) {
      if (clazzes.getJavaType() == game.getClass()) {
        gameFound = true;
        setGameEntity(clazzes);
      }
    }

    if (!gameFound) {
      Assert.fail("Could not find game class as entity");
    }
  }

  @Test
  public void test3FindPlayerEntity() {
    if (metaData == null) {
      Assert.fail("No MetaModel");
    }

    Lab03Game gameController = controller.getLab03Game();
    Lab01Data lab01Data = controller.getLab01Data();
    List<Object> questions = new ArrayList<>();
    questions.add(lab01Data.getQuestions().get(0));
    questions.add(lab01Data.getQuestions().get(1));
    Object game = gameController.createGame("PlayerName", questions);
    Object player = gameController.getPlayer(game);

    boolean playerFound = false;
    for (EntityType<?> clazzes : metaData.getEntities()) {
      if (clazzes.getJavaType() == player.getClass()) {
        playerFound = true;
      }
    }

    if (!playerFound) {
      Assert.fail("Could not find player class as entity");
    }
  }

  @Test
  public void test4FindReferences() {
    if (metaData == null) {
      Assert.fail("No MetaModel");
    }

    if (gameEntity == null) {
      Assert.fail("No GameEntity found");
    }

    EntityTypeImpl questionEntity = null;
    for (EntityType current : metaData.getEntities()) {
      if (current.getName().toLowerCase().equals("question")) {
        questionEntity = (EntityTypeImpl) current;
      }
    }
    if (questionEntity == null) {
      Assert.fail("No Question Entity found");
    }

    Type answerEntity = null;
    for (EntityType current : metaData.getEntities()) {
      if (current.getName().toLowerCase().equals("answer")) {
        answerEntity = (EntityTypeImpl) current;
      }
    }

    for (EmbeddableType embeddable : metaData.getEmbeddables()) {
      if (embeddable.getJavaType().getSimpleName().toLowerCase().equals("answer") ||
          embeddable.getJavaType().getSimpleName().toLowerCase().equals("answers")) {
        answerEntity = embeddable;
      }
    }

    if (answerEntity == null) {
      Assert.fail("No Answer Entity found");
    }

    boolean correct = false;
    for (Attribute attr : gameEntity.getAttributes()) {
      try {
        PluralAttribute plrAttr = (PluralAttribute) attr;
        if (plrAttr.getElementType().getJavaType() == questionEntity.getJavaType()) {
          correct = true;
        }
        if (plrAttr.getElementType().getJavaType() == answerEntity.getJavaType()) {
          correct = true;
        }
      } catch (Exception ignored) {
      }
    }

    if (!correct) {
      Assert.fail("Could not find any right answer or question reference in game");
    }
  }
}
