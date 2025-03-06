package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

// RR-specific imports
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="FirstRoadRunner")
public class FirstRoadRunner extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(-33, -60, Math.toRadians(180)));
        DcMotor armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        DcMotor slideMotor = hardwareMap.get(DcMotor.class, "slideMotor");
        CRServo wristL = hardwareMap.get(CRServo.class, "rotateIntakeLeft");
        CRServo wristR = hardwareMap.get(CRServo.class, "rotateIntakeRight");
        CRServo intakeMotorLeft = hardwareMap.get(CRServo.class, "intakeMotorLeft"); // EH 1
        CRServo intakeMotorRight= hardwareMap.get(CRServo.class, "intakeMotorRight"); // EH 0
        intakeMotorLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotorRight.setDirection(DcMotorSimple.Direction.FORWARD);

        final int ARM_START = 10;

        final int ARM_ON = 1000;
        final int ARM_PICKUP = 500;
        final int SLIDE_ON = 600;

        final int ARM_POWER = 1;
        final int SLIDE_POWER = 1;

        final double WRIST_UP = 1;
        final double WRIST_DOWN = -1;
        final double WRIST_OFF = 0;

        final double INTAKE_IN = 1;
        final double INTAKE_OUT = 1;
        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(23, -57, Math.toRadians(90)))
                        // starting positions
                        .stopAndAdd(new ArmMove(armMotor, ARM_POWER, ARM_ON))
                        // put on first speciman
                        .strafeToLinearHeading(new Vector2d(0, -35), Math.toRadians(90))
                        .stopAndAdd(new SlideMove(slideMotor, SLIDE_POWER, SLIDE_ON))
                        .stopAndAdd(new WristMove(wristL, wristR, WRIST_UP))
                        .waitSeconds(1)
                        .stopAndAdd(new WristMove(wristL, wristR, WRIST_DOWN))
                        .afterTime(0.5, new WristMove(wristL, wristR, WRIST_OFF))
                        // remember to add wait times
                        // to pickup second speciman
                        .strafeToLinearHeading(new Vector2d(46, -57), Math.toRadians(270))
                        .stopAndAdd(new ArmMove(armMotor, ARM_POWER, ARM_PICKUP))
                        .stopAndAdd(new WristMove(wristL, wristR, WRIST_UP))
                        .waitSeconds(2.5)
                        .stopAndAdd(new IntakeMove(intakeMotorLeft, intakeMotorRight, INTAKE_IN))
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

                        // yay hopefully it worked!!

                        .build());
    }

    public class SlideMove implements Action {
        double power;
        int position;
        DcMotor slideMotor;

        public SlideMove(DcMotor slide, double pw, int pos) {
            this.slideMotor = slide;
            this.power = pw;
            this.position = pos;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            slideMotor.setTargetPosition(position);
            slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slideMotor.setPower(power);
            return false;
        }
    }

    public class ArmMove implements Action {
        double power;
        int position;
        DcMotor armMotor;

        public ArmMove(DcMotor arm, double pw, int pos) {
            this.armMotor = arm;
            this.power = pw;
            this.position = pos;

        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            armMotor.setTargetPosition(position);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armMotor.setPower(power);
            return false;
        }
    }
    public class WristMove implements Action {
        double power;
        CRServo wristL;
        CRServo wristR;

        public WristMove(CRServo left, CRServo right, double pw) {
            this.wristL = left;
            this.wristR = right;
            this.power = pw;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            wristL.setPower(power);
            wristR.setPower(-power);
            return false;
        }
    }

    public class IntakeMove implements Action {
        CRServo leftIntake;
        CRServo rightIntake;
        double power;

        public IntakeMove(CRServo left, CRServo right, double pw)  {
            this.leftIntake = left;
            this.rightIntake = right;
            this.power = pw;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            leftIntake.setPower(-power);
            rightIntake.setPower(power);
        }
    }
}

