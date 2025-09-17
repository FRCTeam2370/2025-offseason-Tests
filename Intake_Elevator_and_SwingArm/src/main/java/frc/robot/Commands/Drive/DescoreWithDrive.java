// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands.Drive;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.Commands.Descore;
import frc.robot.Commands.IntakeAlgae;
import frc.robot.RobotContainer.DescoreState;
import frc.robot.Subsystems.ElevatorSubsystem;
import frc.robot.Subsystems.IntakeSubsystem;
import frc.robot.Subsystems.ManipulatorSubsystem;
import frc.robot.Subsystems.PhotonLocalization;
import frc.robot.Subsystems.ShoulderSubsystem;
import frc.robot.Subsystems.SwerveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DescoreWithDrive extends SequentialCommandGroup {
  PIDController rotationPID = new PIDController(0.075, 0.0, 0.0);
  PIDController YPID = new PIDController(0.0125, 0.0005, 0.0);

  double xVal;
  double yVal;
  double rotVal;

  Command driveCommand;
  /** Creates a new DescoreWithDrive. */
  public DescoreWithDrive(SwerveSubsystem mSwerveSubsystem, DoubleSupplier xSup, Supplier<Pose2d> pose, DoubleSupplier yOffset) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    //TODO: add pid to the rotation
    if(SwerveSubsystem.isBlue()){
      xVal = xSup.getAsDouble();
      yVal = -YPID.calculate(PhotonLocalization.intakeCamToTagY * 100);
      rotVal = rotationPID.calculate(PhotonLocalization.intakeCamToTagRotation * 100);
      driveCommand = new TeleopSwerve(mSwerveSubsystem, ()-> xSup.getAsDouble(), ()-> -YPID.calculate(PhotonLocalization.intakeCamToTagY * 100) + yOffset.getAsDouble(), ()-> rotationPID.calculate(PhotonLocalization.intakeCamToTagRotation * 100), ()-> true);
    }else{
      xVal = -xSup.getAsDouble();
      yVal = YPID.calculate(PhotonLocalization.intakeCamToTagY * 100);
      rotVal = -rotationPID.calculate(PhotonLocalization.intakeCamToTagRotation * 100);
      driveCommand = new TeleopSwerve(mSwerveSubsystem, ()-> -xSup.getAsDouble(), ()-> YPID.calculate(PhotonLocalization.intakeCamToTagY * 100) - yOffset.getAsDouble(), ()-> rotationPID.calculate(PhotonLocalization.intakeCamToTagRotation * 100), ()-> true);
    }

    addCommands(mSwerveSubsystem.PathfindToPose(pose), driveCommand);
  }
}
