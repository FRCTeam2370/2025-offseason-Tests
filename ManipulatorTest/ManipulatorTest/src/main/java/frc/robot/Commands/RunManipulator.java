// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.ManipulatorSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class RunManipulator extends Command {
  ManipulatorSubsystem mManipulatorSubsystem;
  double speed;
  /** Creates a new RunManipulator. */
  public RunManipulator(ManipulatorSubsystem mManipulatorSubsystem, double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.mManipulatorSubsystem = mManipulatorSubsystem;
    this.speed = speed;
    addRequirements(mManipulatorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    ManipulatorSubsystem.runManipulator(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    ManipulatorSubsystem.runManipulator(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
