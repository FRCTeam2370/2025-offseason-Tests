// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.ElevatorSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class elevatorSetPos extends Command {
  private double pos;
  /** Creates a new elevatorSetPos. */
  public elevatorSetPos(ElevatorSubsystem mElevatorSubsystem, double pos) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.pos = pos;
    addRequirements(mElevatorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("running elevatorSetPos()");
    SmartDashboard.putBoolean("In Elevator Command", true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    ElevatorSubsystem.setElevatorPosButMagical(pos);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putBoolean("In Elevator Command", false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(ElevatorSubsystem.elevatorMotor.getPosition().getValueAsDouble() >= pos * 0.95){
      return true;
    }else{
      return false;
    }
  }
}
