// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands.MechanismCommands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.MoveMechanism;
import frc.robot.Commands.RunManipulator;
import frc.robot.Commands.SetIntakePosWithMagic;
import frc.robot.Commands.SetShoulderPos;
import frc.robot.Commands.elevatorSetPos;
import frc.robot.Subsystems.ElevatorSubsystem;
import frc.robot.Subsystems.IntakeSubsystem;
import frc.robot.Subsystems.ManipulatorSubsystem;
import frc.robot.Subsystems.ShoulderSubsystem;
import frc.robot.Subsystems.SuperStructure;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class StowMechanism extends SequentialCommandGroup {
  /** Creates a new StowMechanism. */
  private Command stowCommand;

  public StowMechanism(ElevatorSubsystem mElevatorSubsystem, ShoulderSubsystem mShoulderSubsystem, IntakeSubsystem mIntakeSubsystem, ManipulatorSubsystem mManipulatorSubsystem, Supplier<Command> stowSupplier) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    stowCommand = stowSupplier.get();
    
    addCommands(stowCommand, new RunManipulator(mManipulatorSubsystem, 0));
  }
}
