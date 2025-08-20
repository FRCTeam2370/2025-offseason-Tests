// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands.MechanismCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.MoveMechanism;
import frc.robot.Commands.SetIntakePosWithMagic;
import frc.robot.Commands.SetShoulderPos;
import frc.robot.Commands.elevatorSetPos;
import frc.robot.Subsystems.ElevatorSubsystem;
import frc.robot.Subsystems.IntakeSubsystem;
import frc.robot.Subsystems.ShoulderSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class StowMechanism extends SequentialCommandGroup {
  /** Creates a new StowMechanism. */
  public StowMechanism(ElevatorSubsystem mElevatorSubsystem, ShoulderSubsystem mShoulderSubsystem, IntakeSubsystem mIntakeSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new MoveMechanism(0, 1, false, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem).andThen( new SetIntakePosWithMagic(mIntakeSubsystem, 0)));
  }
}
