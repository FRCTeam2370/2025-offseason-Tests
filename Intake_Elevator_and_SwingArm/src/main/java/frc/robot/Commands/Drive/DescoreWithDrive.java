// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands.Drive;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.Commands.Descore;
import frc.robot.Commands.IntakeAlgae;
import frc.robot.RobotContainer.DescoreState;
import frc.robot.Subsystems.ElevatorSubsystem;
import frc.robot.Subsystems.IntakeSubsystem;
import frc.robot.Subsystems.ManipulatorSubsystem;
import frc.robot.Subsystems.ShoulderSubsystem;
import frc.robot.Subsystems.SwerveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DescoreWithDrive extends SequentialCommandGroup {
  /** Creates a new DescoreWithDrive. */
  public DescoreWithDrive(SwerveSubsystem mSwerveSubsystem, DoubleSupplier xSup, Supplier<Pose2d> pose) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(mSwerveSubsystem.PathfindToPose(pose), new TeleopSwerve(mSwerveSubsystem, xSup, ()-> 0, ()-> 0, ()-> true));
  }
}
