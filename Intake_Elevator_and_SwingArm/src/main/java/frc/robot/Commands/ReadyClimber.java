// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Subsystems.ElevatorSubsystem;
import frc.robot.Subsystems.IntakeSubsystem;
import frc.robot.Subsystems.ShoulderSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ReadyClimber extends SequentialCommandGroup {
  /** Creates a new ReadyClimber. */
  public ReadyClimber(IntakeSubsystem mIntakeSubsystem, ElevatorSubsystem mElevatorSubsystem, ShoulderSubsystem mShoulderSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new MoveMechanism(0, 58, false, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem), new SetIntakePosWithMagic(mIntakeSubsystem, -89, true));
  }
}
