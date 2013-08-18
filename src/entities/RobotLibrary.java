//package entities;
//
//import attacks.AttackLibrary;
//import threads.FunctionEverySomeSeconds;
//
//import java.awt.*;
//import java.util.ArrayList;
//
///**
// * Created with IntelliJ IDEA.
// * User: stantonbohmbach
// * Date: 8/12/13
// * Time: 10:26 AM
// * To change this template use File | Settings | File Templates.
// */
//public class RobotLibrary {
//    private List<Robot> robotList = new ArrayList<Robot>();
//
//    public RobotLibrary() {
//        //-------------------------------------Robot 1--------------------------------------------
//        Robot circling = new Robot("Circling", 200, arena.getWidth() / 2 - 1, 10, new Point(-100, 60), 90, 20, 20);
//        circling.setColor(Color.BLUE);
//        Function1 performCircle = new Function1(new Object[] {circling, arena}) {
//            @Override
//            public void execute() {
//                Robot robot = (Robot)args[0];
//                robot.faceCenter((Arena)args[1]);
//            }
//        };
//        Thread circleRobot = new Thread(new FunctionEverySomeSeconds(performCircle, (float).01));
//        threads.add(circleRobot);
//
//        Function1 chainsawAttack = new Function1(new Object[] {circling, attackLibrary}) {
//            @Override
//            public void execute() {
//                ((Robot)args[0]).performAttack(((AttackLibrary)args[1]).attackByName("blowtorch"));
//            }
//        };
//        threads.add(new Thread(new FunctionEverySomeSeconds(chainsawAttack, (float) 10)));
//
//        robotList.add(circling);
//
//        //-----------------------------------Robot 2------------------------------------------
//        Robot chainsaw = new Robot("Chainsaw", 200, 0, 30, new Point(0, 10), 0, "Images/IRobot.png");
//        chainsaw.addAttack(0, attackLibrary.attackByName("chainsaw"));
//
//        Function1 performChainsaw = new Function1(new Object[] {chainsaw}) {
//            @Override
//            public void execute() {
//                ((Robot)args[0]).performRandomAttack();
//            }
//        };
//        threads.add(new Thread(new FunctionEverySomeSeconds(performChainsaw, (float) 7)));
//
//        Function1 circleAround = new Function1(new Object[] {chainsaw}) {
//            @Override
//            public void execute() {
//                ((Robot)args[0]).rotateClockwise(10);
//            }
//        };
//        threads.add(new Thread(new FunctionEverySomeSeconds(circleAround, (float) .25)));
//
//        robots.add(chainsaw);
//
//        //-------------------------------Robot 3-------------------------------------
//        Robot straightLines = new Robot("lines", 200, 100, 100, new Point(40, 0), 45, 40, 40);
//        straightLines.addAttack(0, attackLibrary.attackByName("electric field"));
//
//        Function1 performElectricField = new Function1(new Object[] {straightLines}) {
//            @Override
//            public void execute() {
//                ((Robot)args[0]).performRandomAttack();
//            }
//        };
//        threads.add(new Thread(new FunctionEverySomeSeconds(performElectricField, (float) 12)));
//
//        Function1 switchDirections = new Function1(new Object[] {straightLines}) {
//            @Override
//            public void execute() {
//                Robot robot = (Robot)args[0];
//                if (robot.getMotion().x == 0) {
//                    int motion = robot.getMotion().y;
//                    robot.setMotion(new Point(motion, 0));
//                }
//                else {
//                    int motion = robot.getMotion().x;
//                    robot.setMotion(new Point(0, motion));
//                }
//            }
//        };
//        threads.add(new Thread(new FunctionEverySomeSeconds(switchDirections, (float) 10)));
//
//        robots.add(straightLines);
//
//        //-----------------------------------------------------------------------------------------
//    }
//}
