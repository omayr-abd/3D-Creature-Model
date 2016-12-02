/**
 * 
 */


import java.util.HashMap;
import java.util.Map;

/**
 * @author Omayr Abdelgany U54298732 omaaa@bu.edu
 * @since Fall 2016
 */
public class TestCases extends CyclicIterator<Map<String, Angled>> {

  Map<String, Angled> stop() {
    return this.stop;
  }

  private final Map<String, Angled> stop;

  @SuppressWarnings("unchecked")
  TestCases() {
    this.stop = new HashMap<String, Angled>();
    final Map<String, Angled> flat = new HashMap<String, Angled>();
    final Map<String, Angled> dead = new HashMap<String, Angled>();
    final Map<String, Angled> attack = new HashMap<String, Angled>();
    final Map<String, Angled> web = new HashMap<String, Angled>();

    super.add(stop, flat, dead, attack, web);

    // Flip the spider upsead down in the dead pose
    stop.put(PA2.BODY_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.BODY_NAME, new BaseAngled(0, 0, 0));
    dead.put(PA2.BODY_NAME, new BaseAngled(160, 0, 0));
    attack.put(PA2.BODY_NAME, new BaseAngled(0, 0, 0));
    web.put(PA2.BODY_NAME, new BaseAngled(0, 0, 0));

    stop.put(PA2.HEAD_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.HEAD_NAME, new BaseAngled(0, 0, 0));
    dead.put(PA2.HEAD_NAME, new BaseAngled(0, 0, 0));
    attack.put(PA2.HEAD_NAME, new BaseAngled(0, 0, 0));
    web.put(PA2.HEAD_NAME, new BaseAngled(0, 0, 0));

//    //CAMERA CHANGE 
//    stop.put(PA2.TOP_LEVEL_NAME, new BaseAngled(0, 0, 0));
//    flat.put(PA2.TOP_LEVEL_NAME, new BaseAngled(0, 0, 0));
//    dead.put(PA2.TOP_LEVEL_NAME, new BaseAngled(0, 0, 0));
//    shaka.put(PA2.TOP_LEVEL_NAME, new BaseAngled(0, 0, 0));
//    spread.put(PA2.TOP_LEVEL_NAME, new BaseAngled(0, 0, 0));
//    claw.put(PA2.TOP_LEVEL_NAME, new BaseAngled(0, 0, 0));
//    
    
    // the stop pose
    stop.put(PA2.LEG1_DISTAL_NAME, new BaseAngled(-90, 0, 0));
    stop.put(PA2.LEG1_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.LEG1_BODY_NAME, new BaseAngled(0, 130, 0));
    stop.put(PA2.LEG1_ROTATOR_NAME, new BaseAngled(0, 0, -50));
    stop.put(PA2.LEG2_DISTAL_NAME, new BaseAngled(-90, 0, 0));
    stop.put(PA2.LEG2_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.LEG2_BODY_NAME, new BaseAngled(0, -130, 0));
    stop.put(PA2.LEG2_ROTATOR_NAME, new BaseAngled(0, 0, 50));
    stop.put(PA2.LEG3_DISTAL_NAME, new BaseAngled(-90, 0, 0));
    stop.put(PA2.LEG3_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.LEG3_BODY_NAME, new BaseAngled(0, 105, 0));
    stop.put(PA2.LEG3_ROTATOR_NAME, new BaseAngled(0, 0, -50));
    stop.put(PA2.LEG4_DISTAL_NAME, new BaseAngled(-90, 0, 0));
    stop.put(PA2.LEG4_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.LEG4_BODY_NAME, new BaseAngled(0, -105, 0));
    stop.put(PA2.LEG4_ROTATOR_NAME, new BaseAngled(0, 0, 50));
    stop.put(PA2.LEG5_DISTAL_NAME, new BaseAngled(-90, 0, 0));
    stop.put(PA2.LEG5_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.LEG5_BODY_NAME, new BaseAngled(0, 85, 0));
    stop.put(PA2.LEG5_ROTATOR_NAME, new BaseAngled(0, 0, -50));
    stop.put(PA2.LEG6_DISTAL_NAME, new BaseAngled(-90, 0, 0));
    stop.put(PA2.LEG6_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.LEG6_BODY_NAME, new BaseAngled(0, -85, 0));
    stop.put(PA2.LEG6_ROTATOR_NAME, new BaseAngled(0, 0, 50));
    stop.put(PA2.LEG7_DISTAL_NAME, new BaseAngled(-90, 0, 0));
    stop.put(PA2.LEG7_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.LEG7_BODY_NAME, new BaseAngled(0, 60, 0));
    stop.put(PA2.LEG7_ROTATOR_NAME, new BaseAngled(0, 0, -50));
    stop.put(PA2.LEG8_DISTAL_NAME, new BaseAngled(-90, 0, 0));
    stop.put(PA2.LEG8_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.LEG8_BODY_NAME, new BaseAngled(0, -60, 0));
    stop.put(PA2.LEG8_ROTATOR_NAME, new BaseAngled(0, 0, 50));
    stop.put(PA2.LEG9_PINCER_DISTAL_NAME, new BaseAngled(-90, 0, 0));
    stop.put(PA2.LEG9_PINCER_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.LEG9_PINCER_BODY_NAME, new BaseAngled(0, 20, 0));
    stop.put(PA2.LEG9_PINCER_ROTATOR_NAME, new BaseAngled(50, 0, 0));
    stop.put(PA2.LEG10_PINCER_DISTAL_NAME, new BaseAngled(-90, 0, 0));
    stop.put(PA2.LEG10_PINCER_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    stop.put(PA2.LEG10_PINCER_BODY_NAME, new BaseAngled(0, -20, 0));
    stop.put(PA2.LEG10_PINCER_ROTATOR_NAME, new BaseAngled(50, 0, 0));

    // the flat pose
    flat.put(PA2.LEG1_DISTAL_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG1_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG1_BODY_NAME, new BaseAngled(0, 130, 0));
    flat.put(PA2.LEG1_ROTATOR_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG2_DISTAL_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG2_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG2_BODY_NAME, new BaseAngled(0, -130, 0));
    flat.put(PA2.LEG2_ROTATOR_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG3_DISTAL_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG3_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG3_BODY_NAME, new BaseAngled(0, 105, 0));
    flat.put(PA2.LEG3_ROTATOR_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG4_DISTAL_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG4_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG4_BODY_NAME, new BaseAngled(0, -105, 0));
    flat.put(PA2.LEG4_ROTATOR_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG5_DISTAL_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG5_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG5_BODY_NAME, new BaseAngled(0, 85, 0));
    flat.put(PA2.LEG5_ROTATOR_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG6_DISTAL_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG6_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG6_BODY_NAME, new BaseAngled(0, -85, 0));
    flat.put(PA2.LEG6_ROTATOR_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG7_DISTAL_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG7_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG7_BODY_NAME, new BaseAngled(0, 60, 0));
    flat.put(PA2.LEG7_ROTATOR_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG8_DISTAL_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG8_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG8_BODY_NAME, new BaseAngled(0, -60, 0));
    flat.put(PA2.LEG8_ROTATOR_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG9_PINCER_DISTAL_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG9_PINCER_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG9_PINCER_BODY_NAME, new BaseAngled(0, 20, 0));
    flat.put(PA2.LEG9_PINCER_ROTATOR_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG10_PINCER_DISTAL_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG10_PINCER_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    flat.put(PA2.LEG10_PINCER_BODY_NAME, new BaseAngled(0, -20, 0));
    flat.put(PA2.LEG10_PINCER_ROTATOR_NAME, new BaseAngled(0, 0, 0));

    // the dead pose
    dead.put(PA2.LEG1_DISTAL_NAME, new BaseAngled(-60, 0, 0));
    dead.put(PA2.LEG1_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    dead.put(PA2.LEG1_BODY_NAME, new BaseAngled(0, 130, 0));
    dead.put(PA2.LEG1_ROTATOR_NAME, new BaseAngled(30, 0, 60));
    dead.put(PA2.LEG2_DISTAL_NAME, new BaseAngled(-60, 0, 0));
    dead.put(PA2.LEG2_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    dead.put(PA2.LEG2_BODY_NAME, new BaseAngled(0, -130, 0));
    dead.put(PA2.LEG2_ROTATOR_NAME, new BaseAngled(30, 0, -60));
    dead.put(PA2.LEG3_DISTAL_NAME, new BaseAngled(-60, 0, 0));
    dead.put(PA2.LEG3_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    dead.put(PA2.LEG3_BODY_NAME, new BaseAngled(0, 105, 0));
    dead.put(PA2.LEG3_ROTATOR_NAME, new BaseAngled(10, 0, 60));
    dead.put(PA2.LEG4_DISTAL_NAME, new BaseAngled(-60, 0, 0));
    dead.put(PA2.LEG4_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    dead.put(PA2.LEG4_BODY_NAME, new BaseAngled(0, -105, 0));
    dead.put(PA2.LEG4_ROTATOR_NAME, new BaseAngled(10, 0, -60));
    dead.put(PA2.LEG5_DISTAL_NAME, new BaseAngled(-60, 0, 0));
    dead.put(PA2.LEG5_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    dead.put(PA2.LEG5_BODY_NAME, new BaseAngled(0, 85, 0));
    dead.put(PA2.LEG5_ROTATOR_NAME, new BaseAngled(-10, 0, 60));
    dead.put(PA2.LEG6_DISTAL_NAME, new BaseAngled(-60, 0, 0));
    dead.put(PA2.LEG6_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    dead.put(PA2.LEG6_BODY_NAME, new BaseAngled(0, -85, 0));
    dead.put(PA2.LEG6_ROTATOR_NAME, new BaseAngled(-10, 0, -60));
    dead.put(PA2.LEG7_DISTAL_NAME, new BaseAngled(-60, 0, 0));
    dead.put(PA2.LEG7_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    dead.put(PA2.LEG7_BODY_NAME, new BaseAngled(0, 60, 0));
    dead.put(PA2.LEG7_ROTATOR_NAME, new BaseAngled(-30, 0, 60));
    dead.put(PA2.LEG8_DISTAL_NAME, new BaseAngled(-60, 0, 0));
    dead.put(PA2.LEG8_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    dead.put(PA2.LEG8_BODY_NAME, new BaseAngled(0, -60, 0));
    dead.put(PA2.LEG8_ROTATOR_NAME, new BaseAngled(-30, 0, -60));
    dead.put(PA2.LEG9_PINCER_DISTAL_NAME, new BaseAngled(-120, 0, 0));
    dead.put(PA2.LEG9_PINCER_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    dead.put(PA2.LEG9_PINCER_BODY_NAME, new BaseAngled(0, 20, 0));
    dead.put(PA2.LEG9_PINCER_ROTATOR_NAME, new BaseAngled(-50, 0, 0));
    dead.put(PA2.LEG10_PINCER_DISTAL_NAME, new BaseAngled(-120, 0, 0));
    dead.put(PA2.LEG10_PINCER_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    dead.put(PA2.LEG10_PINCER_BODY_NAME, new BaseAngled(0, -20, 0));
    dead.put(PA2.LEG10_PINCER_ROTATOR_NAME, new BaseAngled(-50, 0, 0));
    
    // the attack pose
    attack.put(PA2.LEG1_DISTAL_NAME, new BaseAngled(-90, 0, 0));
    attack.put(PA2.LEG1_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    attack.put(PA2.LEG1_BODY_NAME, new BaseAngled(0, 130, 0));
    attack.put(PA2.LEG1_ROTATOR_NAME, new BaseAngled(0, 0, -20));
    attack.put(PA2.LEG2_DISTAL_NAME, new BaseAngled(-90, 0, 0));
    attack.put(PA2.LEG2_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    attack.put(PA2.LEG2_BODY_NAME, new BaseAngled(0, -130, 0));
    attack.put(PA2.LEG2_ROTATOR_NAME, new BaseAngled(0, 0, 20));
    attack.put(PA2.LEG3_DISTAL_NAME, new BaseAngled(-80, -20, 0));
    attack.put(PA2.LEG3_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    attack.put(PA2.LEG3_BODY_NAME, new BaseAngled(0, 105, 0));
    attack.put(PA2.LEG3_ROTATOR_NAME, new BaseAngled(0, 0, -40));
    attack.put(PA2.LEG4_DISTAL_NAME, new BaseAngled(-80, 20, 0));
    attack.put(PA2.LEG4_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    attack.put(PA2.LEG4_BODY_NAME, new BaseAngled(0, -105, 0));
    attack.put(PA2.LEG4_ROTATOR_NAME, new BaseAngled(0, 0, 40));
    attack.put(PA2.LEG5_DISTAL_NAME, new BaseAngled(-70, -50, 0));
    attack.put(PA2.LEG5_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    attack.put(PA2.LEG5_BODY_NAME, new BaseAngled(0, 85, 0));
    attack.put(PA2.LEG5_ROTATOR_NAME, new BaseAngled(0, 0, -60));
    attack.put(PA2.LEG6_DISTAL_NAME, new BaseAngled(-70,50, 0));
    attack.put(PA2.LEG6_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    attack.put(PA2.LEG6_BODY_NAME, new BaseAngled(0, -85, 0));
    attack.put(PA2.LEG6_ROTATOR_NAME, new BaseAngled(0, 0, 60));
    attack.put(PA2.LEG7_DISTAL_NAME, new BaseAngled(-60, 0, 0));
    attack.put(PA2.LEG7_MIDDLE_NAME, new BaseAngled(0, 0, 60));
    attack.put(PA2.LEG7_BODY_NAME, new BaseAngled(0, 60, 0));
    attack.put(PA2.LEG7_ROTATOR_NAME, new BaseAngled(0, 0, -80));
    attack.put(PA2.LEG8_DISTAL_NAME, new BaseAngled(-60, 0, 0));
    attack.put(PA2.LEG8_MIDDLE_NAME, new BaseAngled(0, 0, -60));
    attack.put(PA2.LEG8_BODY_NAME, new BaseAngled(0, -60, 0));
    attack.put(PA2.LEG8_ROTATOR_NAME, new BaseAngled(0, 0, 80));
    attack.put(PA2.LEG9_PINCER_DISTAL_NAME, new BaseAngled(-90, 0, 0));
    attack.put(PA2.LEG9_PINCER_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    attack.put(PA2.LEG9_PINCER_BODY_NAME, new BaseAngled(0, 20, 0));
    attack.put(PA2.LEG9_PINCER_ROTATOR_NAME, new BaseAngled(50, 0, 0));
    attack.put(PA2.LEG10_PINCER_DISTAL_NAME, new BaseAngled(-90, 0, 0));
    attack.put(PA2.LEG10_PINCER_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    attack.put(PA2.LEG10_PINCER_BODY_NAME, new BaseAngled(0, -20, 0));
    attack.put(PA2.LEG10_PINCER_ROTATOR_NAME, new BaseAngled(50, 0, 0));

    // the spread pose
    web.put(PA2.LEG1_DISTAL_NAME, new BaseAngled(-80, 0, 0));
    web.put(PA2.LEG1_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    web.put(PA2.LEG1_BODY_NAME, new BaseAngled(0, 130, 0));
    web.put(PA2.LEG1_ROTATOR_NAME, new BaseAngled(-50, 0, 65));
    web.put(PA2.LEG2_DISTAL_NAME, new BaseAngled(-80, 0, 0));
    web.put(PA2.LEG2_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    web.put(PA2.LEG2_BODY_NAME, new BaseAngled(0, -130, 0));
    web.put(PA2.LEG2_ROTATOR_NAME, new BaseAngled(-50, 0, -65));
    web.put(PA2.LEG3_DISTAL_NAME, new BaseAngled(-80, 0, 0));
    web.put(PA2.LEG3_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    web.put(PA2.LEG3_BODY_NAME, new BaseAngled(0, 105, 0));
    web.put(PA2.LEG3_ROTATOR_NAME, new BaseAngled(-50, 0, 75));
    web.put(PA2.LEG4_DISTAL_NAME, new BaseAngled(-80, 0, 0));
    web.put(PA2.LEG4_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    web.put(PA2.LEG4_BODY_NAME, new BaseAngled(0, -105, 0));
    web.put(PA2.LEG4_ROTATOR_NAME, new BaseAngled(-50, 0, -75));
    web.put(PA2.LEG5_DISTAL_NAME, new BaseAngled(-10, 0, 0));
    web.put(PA2.LEG5_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    web.put(PA2.LEG5_BODY_NAME, new BaseAngled(0, 85, 0));
    web.put(PA2.LEG5_ROTATOR_NAME, new BaseAngled(0, 0, 10));
    web.put(PA2.LEG6_DISTAL_NAME, new BaseAngled(-10, 0, 0));
    web.put(PA2.LEG6_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    web.put(PA2.LEG6_BODY_NAME, new BaseAngled(0, -85, 0));
    web.put(PA2.LEG6_ROTATOR_NAME, new BaseAngled(0, 0, -10));
    web.put(PA2.LEG7_DISTAL_NAME, new BaseAngled(-10, 0, 0));
    web.put(PA2.LEG7_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    web.put(PA2.LEG7_BODY_NAME, new BaseAngled(0, 60, 0));
    web.put(PA2.LEG7_ROTATOR_NAME, new BaseAngled(0, 0, 10));
    web.put(PA2.LEG8_DISTAL_NAME, new BaseAngled(-10, 0, 0));
    web.put(PA2.LEG8_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    web.put(PA2.LEG8_BODY_NAME, new BaseAngled(0, -60, 0));
    web.put(PA2.LEG8_ROTATOR_NAME, new BaseAngled(0, 0, -10));
    web.put(PA2.LEG9_PINCER_DISTAL_NAME, new BaseAngled(-45, 0, 0));
    web.put(PA2.LEG9_PINCER_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    web.put(PA2.LEG9_PINCER_BODY_NAME, new BaseAngled(0, 20, 0));
    web.put(PA2.LEG9_PINCER_ROTATOR_NAME, new BaseAngled(50, 0, 0));
    web.put(PA2.LEG10_PINCER_DISTAL_NAME, new BaseAngled(-45, 0, 0));
    web.put(PA2.LEG10_PINCER_MIDDLE_NAME, new BaseAngled(0, 0, 0));
    web.put(PA2.LEG10_PINCER_BODY_NAME, new BaseAngled(0, -20, 0));
    web.put(PA2.LEG10_PINCER_ROTATOR_NAME, new BaseAngled(50, 0, 0));
  }
}
