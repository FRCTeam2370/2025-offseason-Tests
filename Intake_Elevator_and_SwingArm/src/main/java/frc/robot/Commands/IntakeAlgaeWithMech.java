// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Subsystems.ElevatorSubsystem;
import frc.robot.Subsystems.IntakeSubsystem;
import frc.robot.Subsystems.ShoulderSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IntakeAlgaeWithMech extends SequentialCommandGroup {
  /** Creates a new IntakeAlgaeWithMech. */
  public IntakeAlgaeWithMech(double intakeSpeed, IntakeSubsystem mIntakeSubsystem, ElevatorSubsystem mElevatorSubsystem, ShoulderSubsystem mShoulderSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new MoveMechanism(12.5, 1, false, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem), new IntakeAlgaeToBowl(intakeSpeed, mIntakeSubsystem),new WaitCommand(0.5), new StowWithAlgaeInBucket(mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem));
  }
}
