// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import java.lang.constant.Constable;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Subsystems.ElevatorSubsystem;
import frc.robot.Subsystems.IntakeSubsystem;
import frc.robot.Subsystems.ShoulderSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class MoveMechanism extends Command {
  boolean isFinished = false;
  double elevatorPos, shoulderPos;
  boolean shouldStowIntake = true;
  double curElevPos, curIntakePos, curShoulderPos;
  boolean shouldMoveIntake = true;
  /** Creates a new MoveMechanism. */
  public MoveMechanism(double elevatorPos, double shoulderPos, boolean shouldStowIntake, IntakeSubsystem mIntakeSubsystem, ShoulderSubsystem mShoulderSubsystem, ElevatorSubsystem mElevatorSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.elevatorPos = elevatorPos;
    this.shoulderPos = shoulderPos;
    this.shouldStowIntake = shouldStowIntake;
    addRequirements(mIntakeSubsystem, mElevatorSubsystem, mShoulderSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    isFinished = false;
    SmartDashboard.putBoolean("Finished Mechanism Command", false);
    curElevPos = ElevatorSubsystem.getElevatorPos();
    curIntakePos = IntakeSubsystem.getIntakePos();
    curShoulderPos = ShoulderSubsystem.getShoulderPos();

    //Logic to determine whether or not to move the intake on start
    if(elevatorPos >= Constants.MechanismConstants.EMinElev){
      if(curElevPos < Constants.MechanismConstants.EMinElev * Constants.MechanismConstants.minimizerFactor){
        if(shoulderPos >= Constants.MechanismConstants.SMinShould && curShoulderPos >= Constants.MechanismConstants.SMinShould){
          shouldMoveIntake = false;
        }else if(curShoulderPos < Constants.MechanismConstants.SMinShould){
          shouldMoveIntake = true;
        }
      }
      if(curElevPos >= Constants.MechanismConstants.EMinElev * Constants.MechanismConstants.minimizerFactor){
        shouldMoveIntake = false;
      }
    }else if(elevatorPos < Constants.MechanismConstants.EMinElev){
      if(shoulderPos >= Constants.MechanismConstants.SMinShould && curShoulderPos >= Constants.MechanismConstants.SMinShould){
        shouldMoveIntake = false;
      }else if(curShoulderPos < Constants.MechanismConstants.SMinShould){
        shouldMoveIntake = true;
      }
    }
    if(shoulderPos == 1){
      shouldMoveIntake = true;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putBoolean("Is Finished Move Mechanism command", isFinished);
    if(shouldMoveIntake){
      SmartDashboard.putBoolean("In don't move intake", false);
      if(shoulderPos > 0){
        IntakeSubsystem.setIntakeMagicPose(Constants.MechanismConstants.XMinInt);
      }else{
        IntakeSubsystem.setIntakeMagicPose(Constants.MechanismConstants.LMinIntEle);
      }

      if(IntakeSubsystem.getIntakePos() <= Constants.MechanismConstants.LMinIntEle * Constants.MechanismConstants.minimizerFactor){
        ElevatorSubsystem.setElevatorPosButMagical(elevatorPos);
      }

      if((IntakeSubsystem.getIntakePos() <= Constants.MechanismConstants.XMinInt * Constants.MechanismConstants.minimizerFactor || ElevatorSubsystem.getElevatorPos() >= Constants.MechanismConstants.EMinElev * Constants.MechanismConstants.minimizerFactor) && shoulderPos > 0){
        // if(shoulderPos < Constants.MechanismConstants.SMinShould && elevatorPos < Constants.MechanismConstants.EMinElev){
        //   ShoulderSubsystem.setShoulderWithMagic(Constants.MechanismConstants.SMinShould);
        // }
        ShoulderSubsystem.setShoulderWithMagic(shoulderPos);
      }

      if((ElevatorSubsystem.getElevatorPos() >= elevatorPos * Constants.MechanismConstants.minimizerFactor && ElevatorSubsystem.getElevatorPos() <= elevatorPos * Constants.MechanismConstants.maximizerFactor && ElevatorSubsystem.getElevatorPos() >= Constants.MechanismConstants.EMinElev * Constants.MechanismConstants.minimizerFactor) || (ShoulderSubsystem.getShoulderPos() >= shoulderPos * Constants.MechanismConstants.minimizerFactor && ShoulderSubsystem.getShoulderPos() <= shoulderPos * Constants.MechanismConstants.maximizerFactor && ShoulderSubsystem.getShoulderPos() >= Constants.MechanismConstants.SMinShould * Constants.MechanismConstants.minimizerFactor) && shouldStowIntake){
        IntakeSubsystem.setIntakeMagicPose(0);
        if(IntakeSubsystem.getIntakePos() >= -0.5){
          // isFinished = true;
        }
      }
      // }else if(ElevatorSubsystem.getElevatorPos() >= elevatorPos * Constants.MechanismConstants.minimizerFactor && ElevatorSubsystem.getElevatorPos() <= elevatorPos * Constants.MechanismConstants.maximizerFactor && ShoulderSubsystem.getShoulderPos() >= shoulderPos * Constants.MechanismConstants.minimizerFactor && ShoulderSubsystem.getShoulderPos() <= shoulderPos * Constants.MechanismConstants.maximizerFactor){
       
      // }
      if(elevatorPos == 0 && shoulderPos == 1 && ElevatorSubsystem.getElevatorPos() >= elevatorPos * Constants.MechanismConstants.minimizerFactor && ElevatorSubsystem.getElevatorPos() <= elevatorPos * Constants.MechanismConstants.maximizerFactor && ShoulderSubsystem.getShoulderPos() >= shoulderPos * Constants.MechanismConstants.minimizerFactor && ShoulderSubsystem.getShoulderPos() <= shoulderPos * Constants.MechanismConstants.maximizerFactor){
        IntakeSubsystem.setIntakeMagicPose(0);
      }
    }else{
      SmartDashboard.putBoolean("In don't move intake", true);
      ElevatorSubsystem.setElevatorPosButMagical(elevatorPos);

      if(ElevatorSubsystem.getElevatorPos() >= Constants.MechanismConstants.EMinElev * Constants.MechanismConstants.minimizerFactor && shoulderPos > 0){
        // if(shoulderPos < Constants.MechanismConstants.SMinShould && elevatorPos < Constants.MechanismConstants.EMinElev){
        //   ShoulderSubsystem.setShoulderWithMagic(Constants.MechanismConstants.SMinShould);
        // }
        ShoulderSubsystem.setShoulderWithMagic(shoulderPos);
      }

      if(ElevatorSubsystem.getElevatorPos() >= elevatorPos * Constants.MechanismConstants.minimizerFactor && ElevatorSubsystem.getElevatorPos() <= elevatorPos * Constants.MechanismConstants.maximizerFactor && ShoulderSubsystem.getShoulderPos() >= shoulderPos * Constants.MechanismConstants.minimizerFactor && ShoulderSubsystem.getShoulderPos() <= shoulderPos * Constants.MechanismConstants.maximizerFactor){
        //isFinished = true;
      }

      if(elevatorPos == 0 && shoulderPos == 1 && ElevatorSubsystem.getElevatorPos() >= elevatorPos * Constants.MechanismConstants.minimizerFactor && ElevatorSubsystem.getElevatorPos() <= elevatorPos * Constants.MechanismConstants.maximizerFactor && ShoulderSubsystem.getShoulderPos() >= shoulderPos * Constants.MechanismConstants.minimizerFactor && ShoulderSubsystem.getShoulderPos() <= shoulderPos * Constants.MechanismConstants.maximizerFactor){
        IntakeSubsystem.setIntakeMagicPose(0);
      }
    }

    // SmartDashboard.putBoolean("In don't move intake", false);
    //   if(shoulderPos > 0){
    //     IntakeSubsystem.setIntakeMagicPose(Constants.MechanismConstants.XMinInt);
    //   }else{
    //     IntakeSubsystem.setIntakeMagicPose(Constants.MechanismConstants.LMinIntEle);
    //   }

    //   if(IntakeSubsystem.getIntakePos() <= Constants.MechanismConstants.LMinIntEle * Constants.MechanismConstants.minimizerFactor){
    //     ElevatorSubsystem.setElevatorPosButMagical(elevatorPos);
    //   }

    //   if(IntakeSubsystem.getIntakePos() <= Constants.MechanismConstants.XMinInt * Constants.MechanismConstants.minimizerFactor || ElevatorSubsystem.getElevatorPos() >= Constants.MechanismConstants.EMinElev * Constants.MechanismConstants.minimizerFactor){
    //     // if(shoulderPos < Constants.MechanismConstants.SMinShould && elevatorPos < Constants.MechanismConstants.EMinElev){
    //     //   ShoulderSubsystem.setShoulderWithMagic(Constants.MechanismConstants.SMinShould);
    //     // }
    //     ShoulderSubsystem.setShoulderWithMagic(shoulderPos);
    //   }

      // if((ElevatorSubsystem.getElevatorPos() >= elevatorPos * Constants.MechanismConstants.minimizerFactor && ElevatorSubsystem.getElevatorPos() <= elevatorPos * Constants.MechanismConstants.maximizerFactor && ElevatorSubsystem.getElevatorPos() >= Constants.MechanismConstants.EMinElev * Constants.MechanismConstants.minimizerFactor) || (ShoulderSubsystem.getShoulderPos() >= shoulderPos * Constants.MechanismConstants.minimizerFactor && ShoulderSubsystem.getShoulderPos() <= shoulderPos * Constants.MechanismConstants.maximizerFactor && ShoulderSubsystem.getShoulderPos() >= Constants.MechanismConstants.SMinShould * Constants.MechanismConstants.minimizerFactor) && shouldStowIntake){
      //   IntakeSubsystem.setIntakeMagicPose(0);
      // }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putBoolean("Finished Mechanism Command", true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // if(ElevatorSubsystem.getElevatorPos() >= elevatorPos * Constants.MechanismConstants.minimizerFactor){
    //   SmartDashboard.putBoolean("Finished Mechanism Command", true);
    //   return true;
    // }else{
    //   SmartDashboard.putBoolean("Finished Mechanism Command", true);
    //   return false;
    // }
    return false;
  }
}
