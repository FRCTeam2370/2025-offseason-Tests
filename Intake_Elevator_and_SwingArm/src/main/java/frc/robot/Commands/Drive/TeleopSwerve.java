// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands.Drive;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Subsystems.SwerveSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class TeleopSwerve extends Command {
  private SwerveSubsystem mSwerve;
  private DoubleSupplier xSup, ySup, rotSup;
  private BooleanSupplier robotCentricSup;
  private SlewRateLimiter xLimiter = new SlewRateLimiter(7);
  private SlewRateLimiter yLimiter = new SlewRateLimiter(7);
  private SlewRateLimiter rotLimiter = new SlewRateLimiter(7);
  /** Creates a new TeleopSwerve. */
  public TeleopSwerve(SwerveSubsystem mSwerve, DoubleSupplier xSup, DoubleSupplier ySup, DoubleSupplier rotSup, BooleanSupplier robotCentricSup) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.mSwerve = mSwerve;
    addRequirements(mSwerve);
    this.xSup = xSup;
    this.ySup = ySup;
    this.rotSup = rotSup;
    this.robotCentricSup = robotCentricSup;
    SwerveSubsystem.resetGyro();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double xVal = xLimiter.calculate(Math.abs(xSup.getAsDouble()) < Constants.SwerveConstants.Deadband ? 0 : xSup.getAsDouble());
    double yVal = yLimiter.calculate(Math.abs(ySup.getAsDouble()) < Constants.SwerveConstants.Deadband ? 0 : ySup.getAsDouble());
    double rotVal = rotLimiter.calculate(Math.abs(rotSup.getAsDouble()) < Constants.SwerveConstants.Deadband ? 0 : rotSup.getAsDouble());

    if(SwerveSubsystem.isBlue() && !robotCentricSup.getAsBoolean()){
      xVal = -xVal;
      yVal = -yVal;
    }else if(SwerveSubsystem.isBlue() && robotCentricSup.getAsBoolean()){
      xVal = -xVal;
    }
    

    mSwerve.drive(new Translation2d(xVal, yVal).times(Constants.SwerveConstants.maxSpeed), rotVal * 0.1, !robotCentricSup.getAsBoolean(), true);
  }
}
