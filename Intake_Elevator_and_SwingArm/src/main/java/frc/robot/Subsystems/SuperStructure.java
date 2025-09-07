// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import java.util.List;

import edu.wpi.first.math.Pair;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer.DescoreState;
import frc.robot.Commands.MoveMechanism;
import frc.robot.Lib.Utils.SwervePOILogic;

public class SuperStructure extends SubsystemBase {
  static IntakeSubsystem mIntakeSubsystem;
  static ElevatorSubsystem mElevatorSubsystem;
  static ManipulatorSubsystem mManipulatorSubsystem;
  static ShoulderSubsystem mShoulderSubsystem;

  public static DescoreState descoreState;

  /** Creates a new SuperStructure. */
  public SuperStructure(IntakeSubsystem mIntakeSubsystem, ElevatorSubsystem mElevatorSubsystem, ManipulatorSubsystem mManipulatorSubsystem, ShoulderSubsystem mShoulderSubsystem) {
    SuperStructure.mIntakeSubsystem = mIntakeSubsystem;
    SuperStructure.mElevatorSubsystem = mElevatorSubsystem;
    SuperStructure.mShoulderSubsystem = mShoulderSubsystem;
    SuperStructure.mManipulatorSubsystem = mManipulatorSubsystem;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putString("Descore State", getDescoreState().toString());
  }

  public static DescoreState getDescoreState(){
    if(SwervePOILogic.findNearestDescore().getSecond() == true){
      descoreState = DescoreState.UP;
    }else{
      descoreState = DescoreState.LOWER;
    }
    return descoreState;
  }

  public static Pair<Double, Double> getDescoreSetpoints(){
    if(!SwervePOILogic.findNearestDescore().getSecond()){
      return Pair.of(0.0, 15.0);//MoveMechanism(0, 15, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem);
    }else{
      return Pair.of(15.0, 17.0);//new MoveMechanism(15, 17, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem);
    }
    // switch (getDescoreState()){
    //   default:
    //     return null;
    //   case LOWER:
    //     return new MoveMechanism(0, 15, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem);
    //     //break;
    //   case UP:
    //     return new MoveMechanism(15, 17, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem);
        
    // }

    //return returnCommand;
  }
}
