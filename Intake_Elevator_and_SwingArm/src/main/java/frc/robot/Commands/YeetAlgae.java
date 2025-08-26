// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Subsystems.ElevatorSubsystem;
import frc.robot.Subsystems.IntakeSubsystem;
import frc.robot.Subsystems.ManipulatorSubsystem;
import frc.robot.Subsystems.ShoulderSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class YeetAlgae extends SequentialCommandGroup {
  /** Creates a new YeetAlgae. */
  public YeetAlgae(IntakeSubsystem mIntakeSubsystem, ManipulatorSubsystem mManipulatorSubsystem, ElevatorSubsystem mElevatorSubsystem, ShoulderSubsystem mShoulderSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new StowWithAlgaeInBucket(mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem), new IntakeAlgae(0.5, mManipulatorSubsystem), new MoveMechanism(43, 42, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem).alongWith(new YEET(mManipulatorSubsystem)));
  }
}
