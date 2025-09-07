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
public class IntakeCoralFromGround extends SequentialCommandGroup {
  /** Creates a new IntakeCoralFromGround. */
  public IntakeCoralFromGround(double speed, IntakeSubsystem mIntakeSubsystem, ShoulderSubsystem mShoulderSubsystem, ElevatorSubsystem mElevatorSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new SetIntakePosWithMagic(mIntakeSubsystem, -89, true).andThen(new IntakeCoral(speed, mIntakeSubsystem)).andThen(new MoveMechanism(0, 1, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem)));
  }
}
