// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;
import java.util.Optional;

import javax.xml.crypto.dsig.Transform;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;

/** Add your docs here. */
public class PhotonCamera {
    org.photonvision.PhotonCamera camera;
    PhotonPoseEstimator poseEstimator;

    Matrix<N3, N1> curSdDevs;

    public PhotonCamera(String cameraName, Transform3d camToRobot){
        camera = new org.photonvision.PhotonCamera(cameraName);
        poseEstimator = new PhotonPoseEstimator(Constants.PhotonVisionConstants.kTagLayout, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, camToRobot);
        poseEstimator.setMultiTagFallbackStrategy(PoseStrategy.LOWEST_AMBIGUITY);
    }

    private Matrix<N3, N1> getEstSdDevs(Optional<EstimatedRobotPose> estimatedPose, List<PhotonTrackedTarget> results){
    if(estimatedPose.isEmpty()){
      curSdDevs = Constants.PhotonVisionConstants.kSingleTagStdDevs;
      return curSdDevs;
        }else{
            var estSdDevs = Constants.PhotonVisionConstants.kSingleTagStdDevs;
            int numTags = 0;
            double avgDist = 0;

            for(var change : results){
                var tagPose = poseEstimator.getFieldTags().getTagPose(change.getFiducialId());
                if(tagPose.isEmpty()) continue; 

                numTags ++;
                avgDist += tagPose.get().toPose2d().getTranslation().getDistance(estimatedPose.get().estimatedPose.toPose2d().getTranslation());
            }

            if(numTags == 0){
                curSdDevs = Constants.PhotonVisionConstants.kSingleTagStdDevs;
            }else{
                avgDist /= numTags;
                
                if(numTags > 1){
                    curSdDevs = Constants.PhotonVisionConstants.kMultiTagStdDevs;
                }
                if(numTags == 1 && avgDist > 4){
                    estSdDevs = VecBuilder.fill(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
                }else{
                    estSdDevs = estSdDevs.times(1 + (avgDist * avgDist / 30));
                    curSdDevs = estSdDevs;
                }
            }
        return curSdDevs;
        }
    }
}
