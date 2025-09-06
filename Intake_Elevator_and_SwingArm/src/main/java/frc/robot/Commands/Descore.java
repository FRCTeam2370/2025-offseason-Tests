// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer.DescoreState;
import frc.robot.Subsystems.ElevatorSubsystem;
import frc.robot.Subsystems.IntakeSubsystem;
import frc.robot.Subsystems.ManipulatorSubsystem;
import frc.robot.Subsystems.ShoulderSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Descore extends ParallelCommandGroup {
  private Command descoreCommand;
  /** Creates a new Descore. */
  public Descore(DescoreState state, ShoulderSubsystem mShoulderSubsystem, IntakeSubsystem mIntakeSubsystem, ManipulatorSubsystem mManipulatorSubsystem, ElevatorSubsystem mElevatorSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    switch (state){
      case LOWER:
        descoreCommand = new MoveMechanism(0, 15, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem);
        break;
      case UP:
        descoreCommand = new MoveMechanism(15, 17, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem);
    }
    addCommands(descoreCommand, new IntakeAlgae(0.75, mManipulatorSubsystem));
  }
}
