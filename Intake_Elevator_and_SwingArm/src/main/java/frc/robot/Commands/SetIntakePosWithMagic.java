// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.ElevatorSubsystem;
import frc.robot.Subsystems.IntakeSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class SetIntakePosWithMagic extends Command {
  private double pos;
  private double min, max;

  /** Creates a new SetIntakePosWithMagic. */
  public SetIntakePosWithMagic(IntakeSubsystem mIntakeSubsystem, double pos) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.pos = pos;
    addRequirements(mIntakeSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    min = -89.6;
    max = 0;
    System.out.println("running SetIntakePosWithMagic()");
    SmartDashboard.putBoolean("In Intake Command", true);
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  //   double x = ElevatorSubsystem.elevatorMotor.getPosition().getValueAsDouble();
  //   SmartDashboard.putNumber("X", x);
  //  // max = (-30.4 - 3.68*x + 2.18*Math.pow(x, 2) -0.374*Math.pow(x, 3) + 0.0295*Math.pow(x, 4) -0.00109*Math.pow(x, 5) + 0.0000155*Math.pow(x, 6)) - 5;//-30.4\ -3.68x\ +2.18x^{2}-0.374x^{3}+0.0295x^{4}-0.00109x^{5}+0.0000155x^{6}
  //   SmartDashboard.putNumber("Max before logic", max);
  //   if(max > 0 || ElevatorSubsystem.elevatorMotor.getPosition().getValueAsDouble() > 21){
  //     max = -5;
  //   }

  //   if(pos < min){
  //     IntakeSubsystem.setIntakeMagicPose(min);
  //   }else{
  //     IntakeSubsystem.setIntakeMagicPose(pos);
  //   }

  //   if(pos > max){
  //     IntakeSubsystem.setIntakeMagicPose(max);
  //   }else{
  //     IntakeSubsystem.setIntakeMagicPose(pos);
  //   }
    
  //   SmartDashboard.putNumber("Max after logic", max);
  //   SmartDashboard.putNumber("Pos After logic", pos);
  IntakeSubsystem.setIntakeMagicPose(pos);
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putBoolean("In Intake Command", false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // if(IntakeSubsystem.IntakePivot.getPosition().getValueAsDouble() >= pos - 0.25 && IntakeSubsystem.IntakePivot.getPosition().getValueAsDouble() <= pos + 0.25){
    //   return true;
    // }else{
    //   return false;
    // }
    return false;
  }
}
