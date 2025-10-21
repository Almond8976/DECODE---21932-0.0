package org.firstinspires.ftc.teamcode.parts;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

public class AllSeeingEye extends OpMode {

    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTagProcessor;

    @Override
    public void init() {

        aprilTagProcessor = new AprilTagProcessor.Builder()
                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Eye"))
                .addProcessor(aprilTagProcessor)
                .build();
    }
    @Override
    public void loop() {

        List<AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();

        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {

                int tagId = detection.id;

                String tagName = detection.metadata.name;


                double poseX = detection.ftcPose.x;
                double poseY = detection.ftcPose.y;
                double poseZ = detection.ftcPose.z;
                double poseYaw = detection.ftcPose.yaw;
                double posePitch = detection.ftcPose.pitch;
                double poseRoll = detection.ftcPose.roll;

            }
        }
    }
    @Override
    public void stop() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }
}
