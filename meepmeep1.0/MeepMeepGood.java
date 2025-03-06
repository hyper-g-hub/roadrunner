package com.example.meepmeepgood;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepGood {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(23, -57, 90))
                // put on first speciman
                .strafeToLinearHeading(new Vector2d(0, -35), Math.toRadians(90))
                .waitSeconds(1)
                // remember to add wait times
                // to pickup second speciman
                .strafeToLinearHeading(new Vector2d(46, -57), Math.toRadians(270))
                .waitSeconds(2.5)
                // put on second speciman
                .strafeToLinearHeading(new Vector2d(0, -35), Math.toRadians(90))
                //do the code from first speciman
                // to in front of first brick
                .strafeTo(new Vector2d(28, -50))
                .splineToConstantHeading(new Vector2d(38, -8), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(44, -8), Math.toRadians(90))
                // drop off first brick and back
                .strafeToLinearHeading(new Vector2d(44, -53.5), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(44, -8), Math.toRadians(90))
                // to in front of second brick
                .strafeToLinearHeading(new Vector2d(54.5, -8), Math.toRadians(90))
                // drop off second brick and back
                .strafeToLinearHeading(new Vector2d(54.5, -53.5), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(54.5, -8), Math.toRadians(90))
                // to in front of third brick
                .strafeToLinearHeading(new Vector2d(60.5, -8), Math.toRadians(90))
                // drop off third brick
                .strafeToLinearHeading(new Vector2d(60.5, -53.5), Math.toRadians(90))
                // Move to pickup first brick
                .strafeToLinearHeading(new Vector2d(46, -57), Math.toRadians(270))
                // to put on first brick
                .strafeToLinearHeading(new Vector2d(0, -35), Math.toRadians(90))
                // Move to pickup second brick
                .strafeToLinearHeading(new Vector2d(46, -57), Math.toRadians(270))
                // to put on second brick
                .strafeToLinearHeading(new Vector2d(0, -35), Math.toRadians(90))
                // Move to pickup third brick
                .strafeToLinearHeading(new Vector2d(46, -57), Math.toRadians(270))
                // to put on third brick
                .strafeToLinearHeading(new Vector2d(0, -35), Math.toRadians(90))

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
