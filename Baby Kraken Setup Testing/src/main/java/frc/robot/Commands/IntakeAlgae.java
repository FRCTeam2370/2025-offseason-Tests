// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.BabyKrakenSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class IntakeAlgae extends Command {
  double intakeSpeed, idleSpeed;
  /** Creates a new IntakeAlgae. */
  public IntakeAlgae(double intakeSpeed, double idleSpeed, BabyKrakenSubsystem mBabyKrakenSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.intakeSpeed = intakeSpeed;
    this.idleSpeed = idleSpeed;
    addRequirements(mBabyKrakenSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(BabyKrakenSubsystem.bKraken.getStatorCurrent().getValueAsDouble() > 30){
      BabyKrakenSubsystem.hasAlgae(true);
    }else{
      BabyKrakenSubsystem.hasAlgae(false);
    }
    
    if(!BabyKrakenSubsystem.hasAlgae){
      BabyKrakenSubsystem.runBabyKraken(intakeSpeed);
    }else{
      BabyKrakenSubsystem.runBabyKraken(idleSpeed);
    }
   
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
