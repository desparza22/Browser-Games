
import java.util.Random;

public class Spawn {

  private Handler handler;
  private HUD hud;

  private Random r;

  private int scoreKeep = 0;

  private GameObject player;

  public Spawn(Handler handler, HUD hud) {
    this.handler = handler;
    this.hud = hud;
    r = new Random();
    // this.player = player;
  }

  public void addPlayer(GameObject player) {
    this.player = player;
  }

  public void tick() {
    scoreKeep++;

    if(scoreKeep >= 150) {
      scoreKeep = 0;
      hud.setLevel(hud.getLevel() + 1);

      if(hud.getLevel() % 4 == 0) {
        int which = r.nextInt(5);
        GameObject scrambler = new KeyScramble((float) r.nextInt(Game.WIDTH), (float) r.nextInt(Game.HEIGHT), ID.KeyScramble, handler);
        handler.addObject(new CountDown(0f, 0f, ID.CountDown, handler, scrambler, 3000));
        if(which == 0) {
          GameObject basic = new BasicEnemy((float) r.nextInt(Game.WIDTH), (float) r.nextInt(Game.HEIGHT), ID.BasicEnemy, handler);
          handler.addObject(new CountDown(0f, 0f, ID.CountDown, handler, basic, 3000));
        } else if(which == 1) {
          GameObject fast = new FastEnemy((float) r.nextInt(Game.WIDTH), (float) r.nextInt(Game.HEIGHT), ID.FastEnemy, handler);
          handler.addObject(new CountDown(0f, 0f, ID.CountDown, handler, fast, 3000));
        } else {
          GameObject smartie = new SmartEnemy((float) r.nextInt(Game.WIDTH), (float) r.nextInt(Game.HEIGHT), ID.SmartEnemy, handler, player);
          handler.addObject(new CountDown(0f, 0f, ID.CountDown, handler, smartie, 3000));
        }
      }
    }
  }
}
