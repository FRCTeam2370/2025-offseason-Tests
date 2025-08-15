// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands.MechanismCommands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Commands.SetIntakePosWithMagic;
import frc.robot.Commands.SetShoulderPos;
import frc.robot.Commands.elevatorSetPos;
import frc.robot.Subsystems.ElevatorSubsystem;
import frc.robot.Subsystems.IntakeSubsystem;
import frc.robot.Subsystems.ShoulderSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SetMechanismToPose extends SequentialCommandGroup {
  /** Creates a new SetMechanismToPose. */
  public SetMechanismToPose(double elevPose, double swingPose, ElevatorSubsystem mElevatorSubsystem, ShoulderSubsystem mShoulderSubsystem, IntakeSubsystem mIntakeSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new SequentialCommandGroup(new SetIntakePosWithMagic(mIntakeSubsystem, -35), new elevatorSetPos(mElevatorSubsystem, elevPose).alongWith(new SetShoulderPos(swingPose, mShoulderSubsystem)), new SetIntakePosWithMagic(mIntakeSubsystem, 0)));
  }
}
