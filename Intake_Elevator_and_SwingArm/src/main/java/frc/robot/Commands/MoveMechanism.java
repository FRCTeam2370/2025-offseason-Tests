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
import frc.robot.Subsystems.ManipulatorSubsystem;
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
    if(ManipulatorSubsystem.hasAlgae || IntakeSubsystem.hasAlgaeInBowl){
      this.elevatorPos = elevatorPos > Constants.MechanismConstants.maxElevatorVal ? Constants.MechanismConstants.maxElevatorVal : elevatorPos < Constants.MechanismConstants.minElevatorValAlg ? Constants.MechanismConstants.minElevatorValAlg : elevatorPos;
      this.shoulderPos = shoulderPos > Constants.MechanismConstants.maxShoulderVal ? Constants.MechanismConstants.maxShoulderVal : shoulderPos < Constants.MechanismConstants.minShoulderValAlg ? Constants.MechanismConstants.minShoulderValAlg : shoulderPos;
    }else{
      this.shoulderPos = shoulderPos > Constants.MechanismConstants.maxShoulderVal ? Constants.MechanismConstants.maxShoulderVal : shoulderPos < Constants.MechanismConstants.minShoulderVal ? Constants.MechanismConstants.minShoulderVal : shoulderPos;
      this.elevatorPos = elevatorPos > Constants.MechanismConstants.maxElevatorVal ? Constants.MechanismConstants.maxElevatorVal : elevatorPos < Constants.MechanismConstants.minElevatorVal ? Constants.MechanismConstants.minElevatorVal : elevatorPos;
    }
    
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
    //First see if there is an algae in the Manipulator
    if(ManipulatorSubsystem.hasAlgae){
        //We first determine if the desired elevator position is >= than E (variables explained in Constants)
      if(elevatorPos >= Constants.MechanismConstants.EMinElevAlg){
        if(curElevPos < Constants.MechanismConstants.EMinElevAlg * Constants.MechanismConstants.minimizerFactor){
          if(shoulderPos >= Constants.MechanismConstants.SMinShouldAlg && curShoulderPos >= Constants.MechanismConstants.SMinShouldAlg){
            shouldMoveIntake = false;
          }else if(curShoulderPos < Constants.MechanismConstants.SMinShouldAlg){
            shouldMoveIntake = true;
          }
        }
        if(curElevPos >= Constants.MechanismConstants.EMinElevAlg * Constants.MechanismConstants.minimizerFactor){
          shouldMoveIntake = false;
        }
      }else if(elevatorPos < Constants.MechanismConstants.EMinElevAlg){
        if(shoulderPos >= Constants.MechanismConstants.SMinShouldAlg && curShoulderPos >= Constants.MechanismConstants.SMinShouldAlg){
          shouldMoveIntake = false;
        }else if(curShoulderPos < Constants.MechanismConstants.SMinShouldAlg){
          shouldMoveIntake = true;
        }
      }
      if(shoulderPos == 1){
        shouldMoveIntake = true;
      }
    }else{
      //We first determine if the desired elevator position is >= than E (variables explained in Constants)
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
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putBoolean("Is Finished Move Mechanism command", isFinished);

    if(ManipulatorSubsystem.hasAlgae)
    {
      if(shouldMoveIntake){
        SmartDashboard.putBoolean("In don't move intake", false);
        if(shoulderPos > 0){
          IntakeSubsystem.setIntakeMagicPose(Constants.MechanismConstants.XMinIntAlg);
        }else{
          IntakeSubsystem.setIntakeMagicPose(Constants.MechanismConstants.LMinIntEleAlg);
        }
  
        if(IntakeSubsystem.getIntakePos() <= Constants.MechanismConstants.LMinIntEleAlg * Constants.MechanismConstants.minimizerFactor){
          ElevatorSubsystem.setElevatorPosButMagical(elevatorPos);
        }
  
        if((IntakeSubsystem.getIntakePos() <= Constants.MechanismConstants.XMinIntAlg * Constants.MechanismConstants.minimizerFactor || ElevatorSubsystem.getElevatorPos() >= Constants.MechanismConstants.EMinElevAlg * Constants.MechanismConstants.minimizerFactor) && shoulderPos > 0){
          ShoulderSubsystem.setShoulderWithMagic(shoulderPos);
        }
  
        if(ElevatorSubsystem.getElevatorPos() >= elevatorPos - 0.5 && ElevatorSubsystem.getElevatorPos() <= elevatorPos + 0.5 && ShoulderSubsystem.getShoulderPos() >= shoulderPos - 0.5 && ShoulderSubsystem.getShoulderPos() <= shoulderPos + 0.5 && shouldStowIntake){
          if(ElevatorSubsystem.getElevatorPos() >= Constants.MechanismConstants.EMinElevAlg - 0.5){
            IntakeSubsystem.setIntakeMagicPose(0);
          }
          if(ShoulderSubsystem.getShoulderPos() >= Constants.MechanismConstants.SMinShouldAlg - 0.5){
            IntakeSubsystem.setIntakeMagicPose(0);
          }
          if(ShoulderSubsystem.getShoulderPos() <= 2 && ElevatorSubsystem.getElevatorPos() <= 5){
            IntakeSubsystem.setIntakeMagicPose(0);
          }
        }
  
      }else{
        SmartDashboard.putBoolean("In don't move intake", true);
        ElevatorSubsystem.setElevatorPosButMagical(elevatorPos);
  
        if(ElevatorSubsystem.getElevatorPos() >= Constants.MechanismConstants.EMinElevAlg * Constants.MechanismConstants.minimizerFactor && shoulderPos > 0){
          ShoulderSubsystem.setShoulderWithMagic(shoulderPos);
        }
      }
    }
    else
    {
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
          ShoulderSubsystem.setShoulderWithMagic(shoulderPos);
        }
  
        if(ElevatorSubsystem.getElevatorPos() >= elevatorPos - 0.5 && ElevatorSubsystem.getElevatorPos() <= elevatorPos + 0.5 && ShoulderSubsystem.getShoulderPos() >= shoulderPos - 0.5 && ShoulderSubsystem.getShoulderPos() <= shoulderPos + 0.5 && shouldStowIntake){
          if(ElevatorSubsystem.getElevatorPos() >= Constants.MechanismConstants.EMinElev - 0.5){
            IntakeSubsystem.setIntakeMagicPose(0);
          }
          if(ShoulderSubsystem.getShoulderPos() >= Constants.MechanismConstants.SMinShould - 0.5){
            IntakeSubsystem.setIntakeMagicPose(0);
          }
          if(ShoulderSubsystem.getShoulderPos() <= 2 && ElevatorSubsystem.getElevatorPos() <= 5){
            IntakeSubsystem.setIntakeMagicPose(0);
          }
        }
  
      }else{
        SmartDashboard.putBoolean("In don't move intake", true);
        ElevatorSubsystem.setElevatorPosButMagical(elevatorPos);
  
        if(ElevatorSubsystem.getElevatorPos() >= Constants.MechanismConstants.EMinElev * Constants.MechanismConstants.minimizerFactor && shoulderPos > 0){
          ShoulderSubsystem.setShoulderWithMagic(shoulderPos);
        }
      }
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putBoolean("Finished Mechanism Command", true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(ElevatorSubsystem.getElevatorPos() >= elevatorPos - 0.5 && ElevatorSubsystem.getElevatorPos() <= elevatorPos + 0.5 && ShoulderSubsystem.getShoulderPos() >= shoulderPos - 0.5 && ShoulderSubsystem.getShoulderPos() <= shoulderPos + 0.5){
      return true;
    }else{
      return false;
    }
  }
}
