// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Commands.RunBabyKrakenAtSpeed;
import frc.robot.Commands.SetBabyKrakenToPos;
import frc.robot.Subsystems.BabyKrakenSubsystem;

public class RobotContainer {
  public static final CommandXboxController driver = new CommandXboxController(0);

  private static final BabyKrakenSubsystem mBabyKrakenSubsystem = new BabyKrakenSubsystem();

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    driver.a().whileTrue(new RunBabyKrakenAtSpeed(0.5, mBabyKrakenSubsystem));

    driver.b().onTrue(new SetBabyKrakenToPos(5, mBabyKrakenSubsystem));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
